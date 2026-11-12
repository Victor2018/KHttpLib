package org.victor.http.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ListData
 * Author: Victor
 * Date: 2026/11/12 10:15
 * Description: 
 * -----------------------------------------------------------------
 */

open class ListItem<T> {
    var datas: List<T>? = null
    var curPage: Int = 0
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
}