package org.victor.khttp.library.data

import org.victor.khttp.library.presenter.OnHttpListener
import kotlin.reflect.KClass

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Request.kt
 * Author: Victor
 * Date: 2018/8/22 15:31
 * Description: 请求方式实体
 * -----------------------------------------------------------------
 */
 class Request(var requestMethod: Int,var responseCls: KClass<Any>?,var listener: OnHttpListener?) {

    companion object {
        const val GET: Int  = 0
        const val POST: Int = 1
        const val UPLOAD: Int = 2
        const val JSOUP: Int = 3
    }
}