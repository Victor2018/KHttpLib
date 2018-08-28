package khttp.victor.org.khttplib.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PhoneCodeRequest.kt
 * Author: Victor
 * Date: 2018/8/28 9:27
 * Description: 
 * -----------------------------------------------------------------
 */
class PhoneCodeRequest {
    var app: String? = null
    var channel: String? = null
    var model: String? = null
    var nettype: Int = 0
    var term: Int = 0
    var ts: Long = 0
    var udid: String? = null
    var uid: Int = 0
    var version: Int = 0
    var version_name: String? = null
    var data: PhoneCodeParm? = null
}