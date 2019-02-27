/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal interface EventObserver {

    fun events(events: List<LogEvent>)

    fun newEvent(event: LogEvent)
}