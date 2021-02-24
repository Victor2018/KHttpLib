package org.victor.http.vm.factory

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers
import org.victor.http.datasource.GankGirlDataSource
import org.victor.http.vm.GankGirlVm


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlVMFactory
 * Author: Victor
 * Date: 2021/2/24 17:32
 * Description: 
 * -----------------------------------------------------------------
 */
class GankGirlVMFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return GankGirlVm(GankGirlDataSource(Dispatchers.IO)) as T
    }
}