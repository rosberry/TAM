/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.domain.session

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Calendar

/**
 * @author Alexei Korshun on 05/03/2019.
 */
internal class SessionInteractor(
        private val context: Context
) {

    private val currentSessionName: String = Calendar.getInstance().timeInMillis.toString()

    fun startSession() {
        var files: Array<String> = context.filesDir.list()
        for (fileName in files) {
            context.deleteFile(fileName)
        }

        val fileContents = "Hello world!"
        context.openFileOutput(currentSessionName, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }

        files = context.filesDir.list()
        Log.d("Files: ", files.joinToString { it })
        for (fileName in files) {
            val fileInputStream = context.openFileInput(fileName)
            val bufferReader = BufferedReader(InputStreamReader(fileInputStream))

            var result = ""
            var line: String? = bufferReader.readLine()

            while (line != null) {
                result += line
                line = bufferReader.readLine()
            }
            Log.d("File $fileName content: ", result)
        }
    }
}