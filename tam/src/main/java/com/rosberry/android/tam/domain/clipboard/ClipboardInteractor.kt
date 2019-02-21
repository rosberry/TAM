/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.domain.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

/**
 * @author Evgeniy Nagibin on 19/02/2019.
 */

class ClipboardInteractor(private val context: Context) {

    private val clipboardManager by lazy {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    fun saveToClipboard(label: CharSequence, message: CharSequence) {
        val clipData: ClipData = ClipData.newPlainText(label, message)
        clipboardManager.primaryClip = clipData
        Toast.makeText(context, "The message has been saved to the clipboard", Toast.LENGTH_SHORT)
            .show()
    }
}