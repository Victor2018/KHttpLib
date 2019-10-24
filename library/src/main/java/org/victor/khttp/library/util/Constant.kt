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
        const val GANK_URL: String = "http://gank.io/api/xiandu/categories"
        const val PHONE_CODE_URL: String = "http://api.zg.xiaoyoureliao.net/cgi-bin/get_phone_code"
        const val UPLOAD_IMG_URL: String = "http://ott.long.tv/v1/auth/avatar/upload"
        const val FACEBOOK_URL: String = "https://www.facebook.com/1541202502800731/videos/1995585847362392/"

    }
}