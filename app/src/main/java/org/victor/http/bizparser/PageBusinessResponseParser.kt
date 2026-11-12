package org.victor.http.bizparser

import org.victor.http.data.ArticleInfo
import org.victor.http.data.BasePageRes
import org.victor.http.lib.bizparser.NormalBusinessResponseParser
import org.victor.http.lib.bizparser.BusinessResponseParser


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PageBusinessResponseParser
 * Author: Victor
 * Date: 2021/2/24 16:39
 * Description: 分页业务响应解析器
 * -----------------------------------------------------------------
 */
object PageBusinessResponseParser :
    BusinessResponseParser<BasePageRes<ArticleInfo>, BasePageRes<ArticleInfo>> by
    NormalBusinessResponseParser(
        isBusinessSuccess = { it.errorCode == "0" },
        businessCode = { it.errorCode },
        businessMessage = { it.errorMsg },
        result = { it }
    )
