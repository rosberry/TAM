/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.domain.session

import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.data.SessionRepository
import com.rosberry.android.tam.utility.FileNameFormatter
import java.util.Calendar

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionInteractor(
        fileNameFormatter: FileNameFormatter,
        private val sessionRepository: SessionRepository
) {

    private val currentSessionName = fileNameFormatter.getFileName(Calendar.getInstance().time)

    init {
        sessionRepository.createSession(currentSessionName)
    }

    fun putEvent(event: LogEvent): Unit = sessionRepository.write(event, currentSessionName)

    fun getSessions(): Array<String> = sessionRepository.listOfSessions()

    fun removeSessions(): Unit = sessionRepository.removeSessions()

    fun removeSession(sessionName: String): Unit = sessionRepository.removeSession(sessionName)
}