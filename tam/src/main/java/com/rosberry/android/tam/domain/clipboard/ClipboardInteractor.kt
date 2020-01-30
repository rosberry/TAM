/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.domain.clipboard

import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.data.ClipboardRepository
import com.rosberry.android.tam.utility.fullTimeFormatMessage

/**
 * @author Evgeniy Nagibin on 19/02/2019.
 */

internal class ClipboardInteractor(
        private val clipboardRepository: ClipboardRepository
) {

    fun saveToClipboard(event: LogEvent) {
        val message = event.fullTimeFormatMessage()
        clipboardRepository.saveToClipBoard(event.tag, message)
    }
}