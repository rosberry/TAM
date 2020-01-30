/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.presentation

import com.rosberry.android.tam.EventObserver
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.R
import com.rosberry.android.tam.Tam
import com.rosberry.android.tam.domain.clipboard.ClipboardInteractor
import com.rosberry.android.tam.ui.log.TamFragment

/**
 * @author Evgeniy Nagibin on 19/02/2019.
 */
internal class TamPresenter(
        private val interactor: ClipboardInteractor
) : EventObserver {

    private var view: TamFragment? = null

    override fun events(events: List<LogEvent>) {
        view?.showEvents(events)
    }

    override fun newEvent(event: LogEvent) {
        view?.addEvent(event)
    }

    fun saveToClipboard(event: LogEvent) {
        interactor.saveToClipboard(event)
        view?.run {
            this.showMessage(R.string.message_copied)
        }
    }

    fun onAttach(view: TamFragment) {
        this.view = view
        Tam.instance().observeEvents(this)
    }

    fun onDetach() {
        view = null
        Tam.instance().removeListener(this)
    }
}