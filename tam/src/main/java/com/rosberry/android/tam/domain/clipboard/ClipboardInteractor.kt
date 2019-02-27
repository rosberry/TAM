/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.domain.clipboard

import com.rosberry.android.tam.Tam
import com.rosberry.android.tam.data.ClipboardRepository
import com.rosberry.android.tam.utility.TimeFormatter

/**
 * @author Evgeniy Nagibin on 19/02/2019.
 */

internal class ClipboardInteractor(
        private val clipboardRepository: ClipboardRepository,
        private val timeFormatter: TimeFormatter
) {

    fun saveToClipboard(event: Tam.LogEvent) {
        val formattedTime: String = timeFormatter.formatTimeAsMessage(event.time.time)
        val message = "$formattedTime: ${event.type}: ${event.tag} ${event.message}"
        clipboardRepository.saveToClipBoard(event.tag, message)
    }
}