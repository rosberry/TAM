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
private const val FILE_NAME_PATTERN = "yyyyMMdd_HHmmss"

fun Date.toFileName(): String = SimpleDateFormat(FILE_NAME_PATTERN, Locale.getDefault()).format(this)