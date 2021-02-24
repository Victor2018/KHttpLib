package org.victor.http.datasource

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.victor.http.data.GankDetailEntity
import org.victor.http.interfaces.IGankGirlDataSource
import org.victor.http.lib.ApiClient
import org.victor.http.lib.data.HttpResult
import org.victor.http.lib.datasource.BaseDataSource
import org.victor.http.service.GankApiService


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlDataSource
 * Author: Victor
 * Date: 2021/2/24 16:42
 * Description: 
 * -----------------------------------------------------------------
 */
class GankGirlDataSource(private val ioDispatcher: CoroutineDispatcher): BaseDataSource(),IGankGirlDataSource {
    override val gankGirlData = MutableLiveData<HttpResult<GankDetailEntity>>()

    override suspend fun fetchGankGirl(page: Int, pageSize: Int) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            gankGirlData.value = fetchGankGirlReq(page,pageSize)
        }

    }

    private suspend fun <T> fetchGankGirlReq(page: Int,pageSize: Int): T = withContext(ioDispatcher) {

        handleRespone(ApiClient.getApiService(GankApiService::class.java)
            .fetchGankGirl(page,pageSize)) as T
    }
}