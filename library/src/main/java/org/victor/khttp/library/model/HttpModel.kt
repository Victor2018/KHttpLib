package org.victor.khttp.library.model

import org.victor.khttp.library.presenter.OnHttpListener

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpModel.kt
 * Author: Victor
 * Date: 2018/8/24 13:25
 * Description: http请求model
 * -----------------------------------------------------------------
 */
interface HttpModel {
    fun sendReuqest (url:String, header:HashMap<String,String>?,parms: String?, listener:OnHttpListener?)
}