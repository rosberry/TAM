/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui

import com.rosberry.android.tam.Tam

/**
 * @author Alexei Korshun on 27/02/2019.
 */
interface ItemClickListener {

    fun click(position: Int)

    fun longClick(logEvent: Tam.LogEvent)
}