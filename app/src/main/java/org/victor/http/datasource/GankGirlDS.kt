package org.victor.http.datasource

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.victor.http.data.BasePageRes
import org.victor.http.data.GankDetailInfo
import org.victor.http.interfaces.IGankGirlDS
import org.victor.http.lib.ApiClient
import org.victor.http.lib.data.HttpResult
import org.victor.http.lib.util.handleResponse
import org.victor.http.service.GankApiService
import org.victor.http.bizparser.PageBusinessResponseParser
import org.victor.http.data.ArticleInfo
import org.victor.http.data.BaseRes


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
class GankGirlDS(private val ioDispatcher: CoroutineDispatcher): IGankGirlDS {

    override val gankGirlData = MutableLiveData<HttpResult<BaseRes<GankDetailInfo>>>()
    override suspend fun fetchGankGirl() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            gankGirlData.value = fetchGankGirlReq()
        }
    }

    override val articleData = MutableLiveData<HttpResult<BasePageRes<ArticleInfo>>>()
    override suspend fun fetchArticle() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            articleData.value = fetchArticleReq()
        }
    }

    private suspend fun fetchGankGirlReq() = withContext(ioDispatcher) {
        ApiClient.getApiService(GankApiService::class.java)
            .fetchGankGirl()
            .handleResponse()
    }

    private suspend fun fetchArticleReq() = withContext(ioDispatcher) {
        ApiClient.getApiService(GankApiService::class.java)
            .fetchArticle()
            .handleResponse(PageBusinessResponseParser)
    }
}