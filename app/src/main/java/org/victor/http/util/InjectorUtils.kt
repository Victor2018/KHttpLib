package org.victor.http.util

import androidx.savedstate.SavedStateRegistryOwner
import org.victor.http.vm.GankGirlVm
import org.victor.http.vm.factory.GankGirlVMFactory


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: InjectorUtils
 * Author: Victor
 * Date: 2021/2/24 17:51
 * Description: 
 * -----------------------------------------------------------------
 */
object InjectorUtils {
    fun provideGankGirlVm(owner: SavedStateRegistryOwner): GankGirlVMFactory {
        return GankGirlVMFactory(owner)
    }
}