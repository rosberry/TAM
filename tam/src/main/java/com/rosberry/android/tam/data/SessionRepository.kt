/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.data

import android.content.Context
import com.rosberry.android.tam.LogEvent
import com.rosberry.android.tam.utility.serialize
import java.io.File

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionRepository(private val context: Context) {

    private val dirName = "tam_sessions"

    private val tamDir: File
        get() = File(context.filesDir, dirName).apply { mkdir() }

    fun createSession(sessionName: String) {
        File(tamDir, "$sessionName.txt")
            .createNewFile()
    }

    fun removeSessions() {
        listOfSessions().asSequence()
            .forEach { fileName -> context.deleteFile(fileName) }
    }

    fun removeSession(sessionName: String) {
        context.deleteFile(sessionName)
    }

    fun write(event: LogEvent, sessionName: String) {
        val fileContents = event.serialize()
        File(tamDir, "$sessionName.txt")
            .appendBytes("\n$fileContents".toByteArray())
    }

    fun listOfSessions(): Array<String> = tamDir.list() ?: arrayOf()

    fun getFileBy(name: String): File? = tamDir.listFiles { file -> file.name == name }
        ?.firstOrNull()
}