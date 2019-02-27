/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rosberry.android.tam.R
import com.rosberry.android.tam.Tam
import com.rosberry.android.tam.di.tamModule
import com.rosberry.android.tam.presentation.TamPresenter
import kotlinx.android.synthetic.main.f_tam.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        startKoin {
            androidContext(this@TamFragment.requireContext().applicationContext)
            androidLogger()
            modules(tamModule)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.f_tam, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Tam.instance()
            .observeEvents(this)
        eventsList.itemAnimator = DefaultItemAnimator()
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
    }

    override fun onPause() {
        presenter.view = null
        super.onPause()
    }

    override fun onDestroyView() {
        Tam.instance()
            .deobserveEvents(this)
        super.onDestroyView()
    }

    override fun onDestroy() {
        stopKoin()
        super.onDestroy()
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