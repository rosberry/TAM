/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.presentation

import com.rosberry.android.tam.R
import com.rosberry.android.tam.Tam
import com.rosberry.android.tam.domain.clipboard.ClipboardInteractor
import com.rosberry.android.tam.ui.TamFragment

/**
 * @author Evgeniy Nagibin on 19/02/2019.
 */
internal class TamPresenter(
        private val interactor: ClipboardInteractor
) {

    var view: TamFragment? = null

    fun saveToClipboard(event: Tam.LogEvent) {
        interactor.saveToClipboard(event)
        view?.run {
            this.showMessage(R.string.message_copied)
        }
    }
}