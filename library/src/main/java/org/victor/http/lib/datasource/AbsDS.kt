package org.victor.http.lib.datasource

import android.text.TextUtils
import android.util.Log
import org.victor.http.lib.adapter.NetworkResponse
import org.victor.http.lib.data.HttpError
import org.victor.http.lib.data.HttpErrorType
import org.victor.http.lib.data.HttpResult


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AbsDS
 * Author: Victor
 * Date: 2021/2/24 16:00
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class AbsDS {
    val TAG = javaClass.simpleName

    fun handleResponse (response: NetworkResponse<Any, HttpError>): HttpResult<Any> {
        Log.e(TAG,"handleResponse()......")
        when (response) {
            is NetworkResponse.Success -> {
                Log.e(TAG,"handleResponse()......Success")
                try {
//                    if (response.body is BaseRes<*>) {
//                        val data = response.body
//                        Log.e(TAG,"handleResponse()......code = ${data.code}")
//                        if (data.code != 1) {
//                            if (data.code == 2001 || data.code == 2002 || data.code == 2003) {
//                                Log.e(TAG,"handleResponse()......签名验签失败,请重新登录")
//                                LiveDataBus.sendMulti(LoginActions.TOKEN_INVALID)
//                                return HttpResult.Error(data.code,"")
//                            }
//                            return HttpResult.Error(data.code,data.getErrorMsg())
//                        }
//
//                        return HttpResult.Success(data)
//                    } else {
                        return HttpResult.Success(response.body)
//                    }
                }  catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG,"handleResponse()......Success-ERROR")
                    return HttpResult.Error(0,e.localizedMessage)
                }
            }
            is NetworkResponse.ApiError -> {
                Log.e(TAG,"handleResponse()......ApiError-response = ${response}")
                var message = response.body.statusMessage
                if (TextUtils.isEmpty(message)) {
//                    message = response.body.statusCode
                    message = "服务器开小差了,请稍后重试!"
                }
                return HttpResult.Error(HttpErrorType.API,message)
            }
            is NetworkResponse.NetworkError -> {
                Log.e(TAG,"handleResponse()......NetworkError")
                var message = response.error.localizedMessage
                message = "网络错误,请检查网络连接"
                return HttpResult.Error(HttpErrorType.NETWORK,message)
            }
            is NetworkResponse.UnknownError -> {
                Log.e(TAG,"handleResponse()......UnknownError = ${response.error.toString()}")
                var message = response.error?.localizedMessage
                message = "服务器开小差了,请稍后重试!"
                return HttpResult.Error(HttpErrorType.UNKNOWN,message)
            }
            is NetworkResponse.ServerError -> {
                Log.e(TAG,"handleResponse()......ServerError")
                var code = response.code
                var error = response.error
                var message = "code = $code,error = $error"
                message = "服务器开小差了,请稍后重试!"
                return HttpResult.Error(HttpErrorType.SERVER,message)
            }
        }
    }
}