/*
 *
 *  Copyright (c) 2019 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam

import com.rosberry.android.tam.domain.clipboard.ClipboardInteractor

/**
 * @author Evgeniy Nagibin on 19/02/2019.
 */
class TamPresenter(private val interactor: ClipboardInteractor) {
    fun saveToClipboard(label: CharSequence, text: CharSequence) {
        interactor.saveToClipboard(label, text)
    }
}