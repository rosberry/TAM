/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam

import java.io.PrintWriter
import java.io.StringWriter
import java.util.Calendar

/**
 * @author Alexei Korshun on 01/11/2018.
 */
class Tam private constructor() {

    private val events: MutableList<LogEvent> = mutableListOf()

    companion object {

        private var instance: Tam? = null
        private val observers: MutableList<EventObserver> = mutableListOf()

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

    fun observeEvents(observer: EventObserver) {
        observers.add(observer)
        observer.events(ArrayList(events))
    }

    fun deobserveEvents(observer: EventObserver) {
        observers.remove(observer)
    }

    internal fun putEvent(event: LogEvent) {
        events.add(event)
        for (observer in observers) {
            observer.newEvent(event)
        }
    }

    interface EventObserver {

        fun events(events: List<LogEvent>)

        fun newEvent(event: LogEvent)
    }

    data class LogEvent(
            val type: LogType,
            val tag: String = "",
            val message: String = ""
    ) {

        val time: Calendar = Calendar.getInstance()
    }

    enum class LogType {
        EVENT, ERROR, WARNING, HTTP
    }
}