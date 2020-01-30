/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.log

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.R
import com.rosberry.android.tam.presentation.TamPresenter
import com.rosberry.android.tam.ui.session.SessionsFragment
import kotlinx.android.synthetic.main.f_tam.*
import org.koin.android.ext.android.inject

/**
 * @author Alexei Korshun on 01/11/2018.
 */
class TamFragment : Fragment() {

    companion object {
        private const val TAG = "TamFragment"

        fun show(fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(android.R.id.content, TamFragment(),
                        TAG)
                .addToBackStack(TAG)
                .commit()
        }
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.f_tam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsList.itemAnimator = DefaultItemAnimator()
        sessionsButton.setOnClickListener {
            fragmentManager?.run {
                this.beginTransaction()
                    .replace(android.R.id.content, SessionsFragment(), "Session")
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
    }

    override fun onPause() {
        presenter.onDetach()
        super.onPause()
    }

    internal fun showEvents(events: List<LogEvent>) {
        adapter = EventsAdapter(ArrayList(events), itemClickListener)
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