/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.utility

import com.rosberry.android.tam.LogEvent

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class LogEventSerializer(
        private val timeFormatter: TimeFormatter
) {

    fun serialize(event: LogEvent): String {
        val eventTime = timeFormatter.formatTimeAsMessage(event.time.time)

        return "[ ${event.type} $eventTime ${event.tag} ${event.message} ]"
    }
}