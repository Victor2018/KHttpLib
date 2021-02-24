package org.victor.http.data


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankDetailEntity
 * Author: Victor
 * Date: 2021/2/24 16:39
 * Description: 
 * -----------------------------------------------------------------
 */
class GankDetailEntity {
    var status: Int = 0
    var page: Int = 0
    var page_count: Int = 0
    var total_counts: Int = 0
    var data: List<GankDetailInfo>? = null
}