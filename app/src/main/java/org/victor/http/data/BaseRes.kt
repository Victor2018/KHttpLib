package org.victor.http.data


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseRes
 * Author: Victor
 * Date: 2021/2/24 16:39
 * Description:
 * -----------------------------------------------------------------
 */
class BaseRes<T> {
    var data: List<T>? = null
    var errorCode: String? = null
    var errorMsg: String? = null
}
