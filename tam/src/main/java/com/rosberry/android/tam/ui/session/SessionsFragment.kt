/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rosberry.android.tam.R
import com.rosberry.android.tam.domain.session.SessionInteractor
import kotlinx.android.synthetic.main.f_sessions.*
import org.koin.android.ext.android.inject

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionsFragment : Fragment() {

    private val sessionInteractor: SessionInteractor by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_sessions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSession()
    }

    private fun showSession() {
        val sessions = sessionInteractor.getSessions()
        val adapter = SessionAdapter(sessions)
        sessionsView.adapter = adapter
    }
}