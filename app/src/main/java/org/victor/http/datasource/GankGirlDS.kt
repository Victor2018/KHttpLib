package org.victor.http.datasource

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.victor.http.data.GankDetailEntity
import org.victor.http.interfaces.IGankGirlDS
import org.victor.http.lib.ApiClient
import org.victor.http.lib.data.HttpResult
import org.victor.http.lib.datasource.AbsDS
import org.victor.http.service.GankApiService


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlDS
 * Author: Victor
 * Date: 2021/2/24 16:42
 * Description: 
 * -----------------------------------------------------------------
 */
class GankGirlDS(private val ioDispatcher: CoroutineDispatcher): AbsDS(),IGankGirlDS {
    override val gankGirlData = MutableLiveData<HttpResult<GankDetailEntity>>()

    override suspend fun fetchGankGirl() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            gankGirlData.value = fetchGankGirlReq()
        }

    }

    private suspend fun <T> fetchGankGirlReq(): T = withContext(ioDispatcher) {

        handleRespone(ApiClient.getApiService(GankApiService::class.java)
            .fetchGankGirl()) as T
    }
}