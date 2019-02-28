/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.utility

import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.LogType

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal class MessageFormatter(
        private val timeFormatter: TimeFormatter
) {

    fun format(event: LogEvent): String {
        val time = timeFormatter.formatTimeAsLog(event.time.time)

        return if (event.type == LogType.HTTP) "[$time] ${event.tag}: ${event.message}"
        else "[$time] ${event.tag}:\n${event.message}"
    }
}