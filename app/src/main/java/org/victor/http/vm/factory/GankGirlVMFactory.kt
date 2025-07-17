package org.victor.http.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers
import org.victor.http.datasource.GankGirlDS
import org.victor.http.lib.vm.BaseVMFactory
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
class GankGirlVMFactory(owner: SavedStateRegistryOwner) : BaseVMFactory(owner) {

    override fun getVM(): ViewModel {
        return GankGirlVm(GankGirlDS(Dispatchers.IO))
    }

}