package org.victor.http.bizparser

import org.victor.http.data.BaseRes
import org.victor.http.lib.bizparser.NormalBusinessResponseParser
import org.victor.http.lib.bizparser.BusinessResponseParser


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpBusinessResponseParser
 * Author: Victor
 * Date: 2021/2/24 16:39
 * Description: 项目统一业务响应解析器
 * -----------------------------------------------------------------
 */
object HttpBusinessResponseParser :
    BusinessResponseParser<BaseRes<*>, BaseRes<*>> by
    NormalBusinessResponseParser(
        isBusinessSuccess = { it.errorCode == "0" },
        businessCode = { it.errorCode },
        businessMessage = { it.errorMsg },
        result = { it }
    )
