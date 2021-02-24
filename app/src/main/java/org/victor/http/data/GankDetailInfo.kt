package org.victor.http.data


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankDetailInfo
 * Author: Victor
 * Date: 2021/2/24 16:39
 * Description: 
 * -----------------------------------------------------------------
 */
class GankDetailInfo {
    var _id: String? = null
    var author: String? = null
    var category: String? = null
    var createdAt: String? = null
    var publishedAt: String? = null
    var desc: String? = null
    var title: String? = null
    var type: String? = null
    var url: String? = null
    var likeCounts: Int = 0
    var stars: Int = 0
    var views: Int = 0
    var images: List<String>? = null
}