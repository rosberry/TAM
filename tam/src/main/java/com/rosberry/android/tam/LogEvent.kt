/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam

import java.util.Calendar

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal data class LogEvent(
        val type: LogType,
        val tag: String = "",
        val message: String = "",
        var isExpanded: Boolean = false
) {

    val time: Calendar = Calendar.getInstance()
}