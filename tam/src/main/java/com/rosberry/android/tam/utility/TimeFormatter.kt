/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.utility

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @author Alexei Korshun on 27/02/2019.
 */
class TimeFormatter {

    private val logsTimeFormat = SimpleDateFormat("HH:mm:SS", Locale.getDefault())
    private val copyTimeFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:SS", Locale.getDefault())

    fun formatTimeAsLog(date: Date): String {
        return logsTimeFormat.format(date)
    }

    fun formatTimeAsMessage(date: Date): String {
        return copyTimeFormat.format(date)
    }
}