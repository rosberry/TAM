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
 * @author Alexei Korshun on 05/03/2019.
 */
internal class FileNameFormatter {

    private val datePattern = "yyyyMMdd_HHmmSS"
    private val dateFormatter: SimpleDateFormat = SimpleDateFormat(datePattern, Locale.getDefault())

    fun getFileName(date: Date): String = dateFormatter.format(date)
}