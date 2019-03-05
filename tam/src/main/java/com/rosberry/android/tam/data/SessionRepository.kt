/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.data

import android.content.Context
import com.rosberry.android.tam.LogEvent
import java.io.File

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionRepository(
        private val context: Context
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
        val fileContents = event.toString()
        context.openFileOutput(sessionName, Context.MODE_APPEND)
            .use {
                it.write("\n$fileContents".toByteArray())
            }
    }
}