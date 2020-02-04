/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.data

import android.content.ClipData
import android.content.ClipboardManager

/**
 * @author Alexei Korshun on 27/02/2019.
 */
internal class ClipboardRepository(
        private val clipboardManager: ClipboardManager
) {

    fun saveToClipBoard(label: String, message: String) {
        val clipData: ClipData = ClipData.newPlainText(label, message)
        clipboardManager.setPrimaryClip(clipData)
    }
}