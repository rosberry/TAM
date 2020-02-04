/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.domain.session

import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.data.SessionRepository
import com.rosberry.android.tam.utility.toFileName
import java.io.File
import java.util.Calendar

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionInteractor(
        private val sessionRepository: SessionRepository
) {

    private val currentSessionName = Calendar.getInstance().time.toFileName()

    init {
        sessionRepository.createSession(currentSessionName)
    }

    fun putEvent(event: LogEvent): Unit = sessionRepository.write(event, currentSessionName)

    fun getSessions(): Array<String> = sessionRepository.listOfSessions()
        .apply { sortDescending() }

    fun getFileBy(name: String): File? = sessionRepository.getFileBy(name)

    fun removeSessions(): Unit = sessionRepository.removeSessions()

    fun removeSession(sessionName: String): Unit = sessionRepository.removeSession(sessionName)
}