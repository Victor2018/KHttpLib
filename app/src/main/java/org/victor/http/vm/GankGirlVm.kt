package org.victor.http.vm

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.victor.http.interfaces.IGankGirlDataSource

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlVm
 * Author: Victor
 * Date: 2021/2/24 17:25
 * Description: 
 * -----------------------------------------------------------------
 */
class GankGirlVm(private val dataSource: IGankGirlDataSource): ViewModel() {

    val gankGirlData = dataSource.gankGirlData
    fun fetchGankGirl() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchGankGirl()
        }
    }
}