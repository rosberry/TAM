/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam

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

        fun init() {
            if (instance == null) instance = Tam()
        }

        fun instance(): Tam {
            return instance!!
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