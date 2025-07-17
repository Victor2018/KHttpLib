package org.victor.http.interfaces

import androidx.lifecycle.LiveData
import org.victor.http.data.GankDetailEntity
import org.victor.http.lib.data.HttpResult


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IGankGirlDS
 * Author: Victor
 * Date: 2021/2/24 16:37
 * Description: 
 * -----------------------------------------------------------------
 */
interface IGankGirlDS {
    val gankGirlData: LiveData<HttpResult<GankDetailEntity>>
    suspend fun fetchGankGirl()
}