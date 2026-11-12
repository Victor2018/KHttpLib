package org.victor.http.service

import org.victor.http.data.ArticleInfo
import org.victor.http.data.BasePageRes
import org.victor.http.data.BaseRes
import org.victor.http.data.GankDetailInfo
import org.victor.http.lib.adapter.NetworkResponse
import org.victor.http.lib.data.HttpError
import org.victor.http.util.WebConfig
import retrofit2.http.GET


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankApiService
 * Author: Victor
 * Date: 2021/2/24 16:45
 * Description: 
 * -----------------------------------------------------------------
 */
interface GankApiService {

    @GET(WebConfig.GANK_GIRL)
    suspend fun fetchGankGirl(): NetworkResponse<BaseRes<GankDetailInfo>, HttpError>

    @GET(WebConfig.ARTICLE)
    suspend fun fetchArticle(): NetworkResponse<BasePageRes<ArticleInfo>, HttpError>
}