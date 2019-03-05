/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam

import android.content.Context
import com.rosberry.android.tam.di.tamModule
import com.rosberry.android.tam.domain.session.SessionInteractor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.ref.WeakReference

/**
 * @author Alexei Korshun on 01/11/2018.
 */
class Tam private constructor() {

    companion object {

        private var instance: Tam? = null
        private val weakObservers: MutableList<WeakReference<EventObserver>> = mutableListOf()

        private lateinit var sessionInteractor: SessionInteractor

        fun init(context: Context) {
            startKoin {
                androidContext(context.applicationContext)
                androidLogger()
                modules(tamModule)
                sessionInteractor = koin.get()
            }
            instance = Tam()
            sessionInteractor.startSession()
        }

        fun stop() {
            stopKoin()
            instance = null
            weakObservers.clear()
        }

        fun instance(): Tam {
            return checkNotNull(instance)
        }

        fun event(name: String, params: Map<String, Any>? = null) {

            val paramsString = params?.let {
                it.asSequence()
                    .joinToString(postfix = ";\n", separator = "\n",
                            transform = { entry -> "${entry.key} - ${entry.value}" })
            } ?: ""

            instance?.putEvent(LogEvent(LogType.EVENT, name, paramsString))
        }

        fun event(name: String, message: String) {

            instance?.putEvent(LogEvent(LogType.EVENT, name, message))
        }

        fun error(error: Throwable) {
            val stringWriter = StringWriter()
            val printWriter = PrintWriter(stringWriter)
            error.printStackTrace(printWriter)
            val stackTrace = stringWriter.toString()

            instance?.putEvent(LogEvent(LogType.ERROR, error::class.java.simpleName, stackTrace))
        }

        fun http(method: String, params: Map<String, String?>) {
            val message: String = params.asSequence()
                .joinToString(postfix = ";\n", separator = "\n",
                        transform = { entry -> "${entry.key}: ${entry.value}" })

            instance?.putEvent(LogEvent(LogType.HTTP, method, message))
        }

        fun warning() {
            instance?.putEvent(LogEvent(LogType.WARNING))
        }
    }

    private val events: MutableList<LogEvent> = mutableListOf()

    internal fun observeEvents(observer: EventObserver) {
        weakObservers.add(WeakReference(observer))
        observer.events(ArrayList(events))
    }

    internal fun deobserveEvents(observer: EventObserver) {
    }

    internal fun putEvent(event: LogEvent) {
        events.add(event)
        for (weak in weakObservers) {
            weak.get()
                ?.newEvent(event)
        }
    }
}