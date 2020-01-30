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
internal fun LogEvent.serialize(): String {
    val eventTime = this.time.time.fullDateFormat()

    return "[ ${this.type} $eventTime ${this.tag} ${this.message} ]"
}