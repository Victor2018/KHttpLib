package org.victor.http.lib.data


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpResult
 * Author: Victor
 * Date: 2021/2/24 16:03
 * Description: 
 * -----------------------------------------------------------------
 */
sealed class HttpResult<out T : Any> {

    class Success<out T : Any>(val value: T) : HttpResult<T>()

    class Error(val code: String?,val message: String?) : HttpResult<Nothing>()
}