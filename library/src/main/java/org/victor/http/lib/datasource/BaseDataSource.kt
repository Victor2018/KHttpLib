package org.victor.http.lib.datasource

import android.text.TextUtils
import android.util.Log
import org.victor.http.lib.adapter.NetworkResponse
import org.victor.http.lib.data.HttpError
import org.victor.http.lib.data.HttpResult


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseDataSource
 * Author: Victor
 * Date: 2021/2/24 16:00
 * Description: 
 * -----------------------------------------------------------------
 */
abstract class BaseDataSource {
    val TAG = javaClass.simpleName

    fun handleRespone (response: NetworkResponse<Any, HttpError>): HttpResult<Any> {
        Log.e(TAG,"handleRespone()......")
        when (response) {
            is NetworkResponse.Success -> {
                Log.e(TAG,"handleRespone()......Success")
                try {
                   /* val data = response.body as BaseReq

                    if (TextUtils.equals(data.code,"001020")) {
                        Log.e(TAG,"handleRespone()......token is invalid will to login")
                        LiveDataBus.send(LoginActions.GO_ONE_KEY_LOGIN)
                    }

                    if (!TextUtils.equals(data.code?.toUpperCase(),"OK")) {
                        return HttpResult.Error(data.code,data.message)
                    }*/

                    return HttpResult.Success(response.body)
                }  catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG,"handleRespone()......Success-ERROR")
                    return HttpResult.Error(null,e.localizedMessage)
                }
            }
            is NetworkResponse.ApiError -> {
                Log.e(TAG,"handleRespone()......ApiError")
                val message = response.body.statusMessage
                return HttpResult.Error(null,message)
            }
            is NetworkResponse.NetworkError -> {
                Log.e(TAG,"handleRespone()......NetworkError")
                val message = response.error.localizedMessage
                return HttpResult.Error(null,message)
            }
            is NetworkResponse.UnknownError -> {
                Log.e(TAG,"handleRespone()......UnknownError" + response.error?.localizedMessage)
                val message = response.error?.localizedMessage

                return HttpResult.Error(null,message)
            }
            is NetworkResponse.ServerError -> {
                Log.e(TAG,"handleRespone()......ServerError")
                var code = response.code
                var error = response.error
                val message = "code = $code,error = $error"
                return HttpResult.Error(null,message)
            }
        }
    }
}