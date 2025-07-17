package org.victor.http.lib.interceptor

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import org.victor.http.lib.BuildConfig
import java.nio.charset.Charset

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LogInterceptor
 * Author: Victor
 * Date: 2022/3/1 12:03
 * Description: 日志拦截器
 * -----------------------------------------------------------------
 */

class LogInterceptor: Interceptor {
    val TAG = "LogInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        Log.e(TAG, "--------------------request-------------------")
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "request url = ${request.url}")
            Log.e(TAG, "request url = ${request.url.toString().replace("http","url")}")
            Log.e(TAG, "request headers = ${Gson().toJson(request.headers)}")
        }

        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        var charset = Charset.forName("UTF-8")
//                val contentType = requestBody?.contentType()
//                var charset = contentType?.charset(UTF8)
        val parm: String = buffer.readString(charset)

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "request parm = $parm")
            Log.e(TAG, "request requestMethod = ${request.method}")
        }

        var response = chain.proceed(request)

        Log.e(TAG, "--------------------repsonse-------------------")
        if (BuildConfig.DEBUG) {
            Log.e(TAG,"repsonse url = ${request.url}")
            Log.e(TAG, "repsonse url = ${request.url.toString().replace("http","url")}")
            Log.e(TAG,"repsonse code = ${response.code}")
            //response.peekBody不会关闭流
            Log.e(TAG,"responseData = ${response.peekBody(1024)?.string()}")
        }
        return response
    }
}