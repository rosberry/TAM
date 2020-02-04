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
private const val SHORT_TIME_FORMAT = "HH:mm:SS"
private const val FULL_TIME_FORMAT = "dd/MM/yyyy HH:mm:SS"

fun Date.shortDateFormat(): String = SimpleDateFormat(SHORT_TIME_FORMAT, Locale.getDefault()).format(this)

fun Date.fullDateFormat(): String = SimpleDateFormat(FULL_TIME_FORMAT, Locale.getDefault()).format(this)