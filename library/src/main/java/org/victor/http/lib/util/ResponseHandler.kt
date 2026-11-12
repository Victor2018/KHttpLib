package org.victor.http.lib.util

import android.text.TextUtils
import android.util.Log
import org.victor.http.lib.ApiClient
import org.victor.http.lib.adapter.NetworkResponse
import org.victor.http.lib.bizparser.BusinessResponseParser
import org.victor.http.lib.data.HttpError
import org.victor.http.lib.data.HttpErrorType
import org.victor.http.lib.data.HttpResult

private const val TAG = "ResponseHandler"

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ResponseHandler
 * Author: Victor
 * Date: 2021/2/24 16:00
 * Description: 网络响应处理扩展
 * -----------------------------------------------------------------
 */

/**
 * 使用已注册的业务解析器处理响应，未注册时按 HTTP 成功直接透传
 */
fun <T : Any> NetworkResponse<T, HttpError>.handleResponse(): HttpResult<T> {
    return handleResponse { body ->
        ApiClient.parseResponse(body) ?: HttpResult.Success(body)
    }
}

/**
 * 使用指定的业务解析器处理响应，适用于需要将结果加工为其他类型的接口
 */
fun <T : Any, R : Any> NetworkResponse<T, HttpError>.handleResponse(
    parser: BusinessResponseParser<T, R>
): HttpResult<R> {
    return handleResponse { body -> parser.parse(body) }
}

private fun <T : Any, R : Any> NetworkResponse<T, HttpError>.handleResponse(
    parse: (T) -> HttpResult<R>
): HttpResult<R> {
    Log.e(TAG, "handleResponse()......")
    when (this) {
        is NetworkResponse.Success -> {
            Log.e(TAG, "handleResponse()......Success")
            try {
                return parse(body)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, "handleResponse()......Success-ERROR")
                return HttpResult.Error("0", e.localizedMessage)
            }
        }

        is NetworkResponse.ApiError -> {
            Log.e(TAG, "handleResponse()......ApiError-response = $this")
            var message = body.statusMessage
            if (TextUtils.isEmpty(message)) {
                message = "服务器开小差了,请稍后重试!"
            }
            return HttpResult.Error(HttpErrorType.API.toString(), message)
        }

        is NetworkResponse.NetworkError -> {
            Log.e(TAG, "handleResponse()......NetworkError")
            var message = error.localizedMessage
            message = "网络错误,请检查网络连接"
            return HttpResult.Error(HttpErrorType.NETWORK.toString(), message)
        }

        is NetworkResponse.UnknownError -> {
            Log.e(TAG, "handleResponse()......UnknownError = ${error.toString()}")
            var message = error?.localizedMessage
            message = "服务器开小差了,请稍后重试!"
            return HttpResult.Error(HttpErrorType.UNKNOWN.toString(), message)
        }

        is NetworkResponse.ServerError -> {
            Log.e(TAG, "handleResponse()......ServerError")
            var message = "code = $code,error = $error"
            message = "服务器开小差了,请稍后重试!"
            return HttpResult.Error(HttpErrorType.SERVER.toString(), message)
        }
    }
}
