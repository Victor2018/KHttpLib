package org.victor.khttp.library.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankInfo.kt
 * Author: Victor
 * Date: 2018/8/24 11:03
 * Description: 
 * -----------------------------------------------------------------
 */
class GankInfo {
    var __id:String? = null//注意fastjson 会丢失_id 故使用__转译_
    var en_name:String? = null
    var name:String? = null
    var rank:Int = 0
}