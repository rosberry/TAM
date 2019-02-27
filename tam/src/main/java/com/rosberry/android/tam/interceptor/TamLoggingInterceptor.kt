/*
 *
 *  * Copyright (c) 2018 Rosberry. All rights reserved.
 *
 */

package com.rosberry.android.tam.interceptor

import com.rosberry.android.tam.Tam
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

/**
 * @author Alexei Korshun on 02/11/2018.
 */
class TamLoggingInterceptor : Interceptor {

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url: String = request.url()
            .encodedPath()

        val requestMethod = request.method()

        val authorizationHeader = request.header("Authorization")

        val requestBody = request.body()
            ?.let {
                val buffer = Buffer().apply { it.writeTo(this) }
                val charset = it.contentType()?.charset(UTF8) ?: UTF8
                buffer.readString(charset)
            }

        val requestParams: Map<String, String?> = mapOf(
                "--->" to url,
                "authorization" to authorizationHeader,
                "body" to requestBody
        )
        Tam.http(requestMethod, requestParams)

        val response: Response
        try {
            response = chain.proceed(request)
        } catch (error: Exception) {
            throw error
        }

        val responseBody = response.body()
            ?.string()

        val responseParams: Map<String, String?> = mapOf(
                "<---" to url,
                "body" to responseBody
        )

        Tam.http(requestMethod, responseParams)
        return chain.proceed(request)
    }
}