package org.victor.khttp.library.model.impl

import org.victor.khttp.library.model.HttpModel
import org.victor.khttp.library.module.HttpRequest
import org.victor.khttp.library.presenter.OnHttpListener

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpModelImpl.kt
 * Author: Victor
 * Date: 2018/8/24 13:28
 * Description: 普通http请求model实现
 * -----------------------------------------------------------------
 */
class HttpModelImpl : HttpModel {
    override fun sendReuqest(url: String, header: HashMap<String, String>?, parms: String?, listener: OnHttpListener?) {
        HttpRequest.instance.sendRequest(url,header,parms,null,listener)
    }
}