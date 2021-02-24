package org.victor.http.service

import org.victor.http.data.GankDetailEntity
import org.victor.http.lib.adapter.NetworkResponse
import org.victor.http.lib.data.HttpError
import org.victor.http.util.WebConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


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
    suspend fun fetchGankGirl(
            @Path("page") page: Int = 0,
            @Path("count") count: Int?): NetworkResponse<GankDetailEntity, HttpError>
}