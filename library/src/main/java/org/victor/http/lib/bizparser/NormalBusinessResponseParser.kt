package org.victor.http.lib.bizparser

import org.victor.http.lib.data.HttpResult


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NormalBusinessResponseParser
 * Author: Victor
 * Date: 2021/2/24 16:00
 * Description: 可配置的通用业务响应解析模板
 * -----------------------------------------------------------------
 */
class NormalBusinessResponseParser<T : Any, R : Any>(
    private val isBusinessSuccess: (T) -> Boolean,
    private val businessCode: (T) -> String?,
    private val businessMessage: (T) -> String?,
    private val result: (T) -> R,
    private val onBusinessError: (HttpResult.Error) -> Unit = {}
) : BusinessResponseParser<T, R> {

    override fun parse(body: T): HttpResult<R> {
        if (!isBusinessSuccess(body)) {
            val error = HttpResult.Error(businessCode(body), businessMessage(body))
            onBusinessError(error)
            return error
        }

        return HttpResult.Success(result(body))
    }
}
