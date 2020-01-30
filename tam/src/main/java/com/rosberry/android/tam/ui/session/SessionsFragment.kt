/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.ui.session

import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import com.rosberry.android.tam.R
import com.rosberry.android.tam.domain.session.SessionInteractor
import kotlinx.android.synthetic.main.f_sessions.*
import org.koin.android.ext.android.inject
import java.io.File

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionsFragment : Fragment() {

    private val sessionInteractor: SessionInteractor by inject()

    private val itemClickListener: (String) -> Unit = { sessionName: String ->
        sessionInteractor.getFileBy(sessionName)
            ?.run { shareFile(this) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.f_sessions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSession()
    }

    private fun showSession() {
        val sessions = sessionInteractor.getSessions()
        val adapter = SessionAdapter(sessions, itemClickListener)
        sessionsView.adapter = adapter
    }

    private fun shareFile(file: File) {
        val contentUri = getUriForFile(requireContext(), "com.rosberry.android.tam.fileprovider", file)
        val intentShareFile = Intent(Intent.ACTION_SEND)
        intentShareFile.data = contentUri
        intentShareFile.flags = FLAG_GRANT_READ_URI_PERMISSION
        intentShareFile.putExtra(Intent.EXTRA_STREAM, contentUri)
        startActivity(Intent.createChooser(intentShareFile, "Share File"))
    }
}