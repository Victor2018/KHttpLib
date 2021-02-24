package org.victor.http.app

import android.app.Application
import org.victor.http.lib.ApiClient


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: App
 * Author: Victor
 * Date: 2021/2/24 19:09
 * Description: 
 * -----------------------------------------------------------------
 */
class App: Application() {
    companion object {
        private var instance : App ?= null
        fun get() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ApiClient.BASE_URL = "https://gank.io/api/v2/"

    }
}