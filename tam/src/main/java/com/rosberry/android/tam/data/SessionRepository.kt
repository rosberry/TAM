/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.data

import android.content.Context
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.utility.LogEventSerializer
import java.io.File

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionRepository(
        private val context: Context,
        private val logEventSerializer: LogEventSerializer
) {

    fun createSession(sessionName: String) {
        File(context.filesDir, sessionName)
    }

    fun removeSessions() {
        val files: Array<String> = context.filesDir.list()
        for (fileName in files) {
            context.deleteFile(fileName)
        }
    }

    fun write(event: LogEvent, sessionName: String) {
        val fileContents = logEventSerializer.serialize(event)
        context.openFileOutput(sessionName, Context.MODE_APPEND)
            .use {
                it.write("\n$fileContents".toByteArray())
            }
    }
}