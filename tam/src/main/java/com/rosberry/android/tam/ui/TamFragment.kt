/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.DefaultItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.R
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
class TamFragment : DialogFragment() {

    companion object {
        private const val TAG = "TamFragment"

        fun show(fragmentManager: FragmentManager) = TamFragment().show(fragmentManager, TamFragment.TAG)
    }

    private lateinit var adapter: EventsAdapter

    private val presenter: TamPresenter by inject()
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val itemClickListener = object : ItemClickListener {
        override fun longClick(logEvent: LogEvent) {
            presenter.saveToClipboard(logEvent)
        }

        override fun click(position: Int) {
            adapter.notifyItemChanged(position)
        }
    }

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

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.f_tam, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsList.itemAnimator = DefaultItemAnimator()
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
    }

    override fun onPause() {
        presenter.onDetach()
        super.onPause()
    }

    override fun onDestroy() {
        stopKoin()
        super.onDestroy()
    }

    internal fun showEvents(events: List<LogEvent>) {
        adapter = EventsAdapter(ArrayList(events), itemClickListener, get())
        eventsList.adapter = adapter
    }

    internal fun addEvent(event: LogEvent) {
        handler.post { adapter.putEvent(event) }
    }

    internal fun showMessage(messageId: Int) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT)
            .show()
    }
}