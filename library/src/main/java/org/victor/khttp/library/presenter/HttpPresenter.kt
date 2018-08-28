package org.victor.khttp.library.presenter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpPresenter.kt
 * Author: Victor
 * Date: 2018/8/24 13:35
 * Description: http请求presenter
 * -----------------------------------------------------------------
 */
interface HttpPresenter {
    fun sendRequest(url: String, header:HashMap<String,String>?, parms: String?)
}