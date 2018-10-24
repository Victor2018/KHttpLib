package org.victor.khttp.library.data

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
 class Request {
    companion object {
        const val GET: Int  = 0
        const val POST: Int = 1
        const val UPLOAD: Int = 2
        const val JSOUP: Int = 3
    }
}