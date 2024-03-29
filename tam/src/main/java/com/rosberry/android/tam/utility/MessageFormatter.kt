/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.utility

import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.LogType
import java.util.Date

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal fun LogEvent.shortTimeFormatMessage(): String = formatMessage(this) { date: Date -> date.shortDateFormat() }

internal fun LogEvent.fullTimeFormatMessage(): String = formatMessage(this) { date: Date -> date.fullDateFormat() }

private inline fun formatMessage(event: LogEvent, dateFormatter: (Date) -> String): String {
    val time = dateFormatter.invoke(event.time.time)

    return if (event.type == LogType.HTTP) "[$time] ${event.tag}: ${event.message}"
    else "[$time] ${event.tag}:\n${event.message}"
}