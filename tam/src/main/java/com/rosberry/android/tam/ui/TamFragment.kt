/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.rosberry.android.tam.R
import com.rosberry.android.tam.Tam
import com.rosberry.android.tam.presentation.TamPresenter
import kotlinx.android.synthetic.main.f_tam.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

/**
 * @author Alexei Korshun on 01/11/2018.
 */
class TamFragment : DialogFragment(), Tam.EventObserver, ItemClickListener {

    companion object {
        const val TAG = "TamFragment"
    }

    private lateinit var adapter: EventsAdapter

    private val presenter: TamPresenter by inject()

    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_tam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tam.instance()
            .observeEvents(this)
        eventsList.itemAnimator = DefaultItemAnimator()
    }

    override fun onResume() {
        presenter.view = this
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params as android.view.WindowManager.LayoutParams
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.view = null
    }

    override fun onDestroyView() {
        Tam.instance()
            .deobserveEvents(this)
        super.onDestroyView()
    }

    override fun events(events: List<Tam.LogEvent>) {
        adapter = EventsAdapter(ArrayList(events), this, get())
        eventsList.adapter = adapter
    }

    override fun newEvent(event: Tam.LogEvent) {
        handler.post { adapter.putEvent(event) }
    }

    override fun click(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun longClick(logEvent: Tam.LogEvent) {
        presenter.saveToClipboard(logEvent)
    }

    fun showMessage(messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT)
            .show()
    }
}