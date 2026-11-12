package org.victor.http.lib.bizparser

import org.victor.http.lib.data.HttpResult


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BusinessResponseParser
 * Author: Victor
 * Date: 2021/2/24 16:00
 * Description: 业务响应解析策略
 * -----------------------------------------------------------------
 */
interface BusinessResponseParser<in T : Any, out R : Any> {
    fun parse(body: T): HttpResult<R>
}
