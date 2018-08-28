package org.victor.khttp.library.util

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Constant.kt
 * Author: Victor
 * Date: 2018/8/22 15:54
 * Description: 常量实体
 * -----------------------------------------------------------------
 */
class Constant {
    companion object {
        const val SEND_GET_REQUEST: Int           = 0x601
        const val SEND_POST_REQUEST: Int          = 0x602
        const val MULTIPART_UPLOAD_REQUEST : Int = 0x603

        const val PAGE_SIZE: Int = 20
        const val GANK_URL: String = "https://gank.io/api/xiandu/categories"

    }
}