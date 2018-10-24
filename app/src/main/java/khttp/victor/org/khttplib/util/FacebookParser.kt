package khttp.victor.org.khttplib.util

import android.text.TextUtils
import org.victor.khttp.library.data.FacebookReq

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: FacebookParser.java
 * Author: Victor
 * Date: 2018/10/24 15:59
 * Description: 
 * -----------------------------------------------------------------
 */
class FacebookParser {
    companion object {
        fun parseFacebook (response: String?): FacebookReq {
            var facebookReq = FacebookReq()
            if (response?.contains("\n")!!) {
                val items = response.split("\n".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                for (i in items.indices) {
                    if (items[i].contains("no_ratelimit")) {
                        val datas = items[i].split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                        for (j in datas.indices) {
                            if (datas[j].contains("hd_src_no_ratelimit") && datas[j].split("\"".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray().size >= 2) {
                                facebookReq.hdPlayUrl = datas[j].split("\"".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[1]
                            }
                            if (datas[j].contains("sd_src_no_ratelimit") && datas[j].split("\"".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray().size >= 2) {
                                facebookReq.sdPlayUrl = datas[j].split("\"".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[1]
                            }
                        }
                    }
                }
            }
            facebookReq.playUrl = facebookReq.hdPlayUrl
            if (TextUtils.isEmpty(facebookReq.playUrl)) {
                facebookReq.playUrl = facebookReq.sdPlayUrl
            }
            return facebookReq
        }
    }
}