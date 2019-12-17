package org.victor.khttp.library.presenter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: OnHttpListener.kt
 * Author: Victor
 * Date: 2018/8/22 15:48
 * Description: http请求回调接口
 * -----------------------------------------------------------------
 */
interface OnHttpListener {
    fun onComplete(data: Any?, msg: String)
}