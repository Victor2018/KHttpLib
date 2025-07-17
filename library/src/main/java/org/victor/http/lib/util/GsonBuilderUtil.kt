package org.victor.http.lib.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hok.lib.coremodel.http.adapter.DoubleNullAdapter
import com.hok.lib.coremodel.http.adapter.IntegerNullAdapter
import com.hok.lib.coremodel.http.adapter.LongNullAdapter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GsonBuilder
 * Author: Victor
 * Date: 2022/7/19 10:19
 * Description: 
 * -----------------------------------------------------------------
 */

object GsonBuilderUtil {

    fun buildGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Int::class.java, IntegerNullAdapter())
            .registerTypeAdapter(Double::class.java, DoubleNullAdapter())
            .registerTypeAdapter(Long::class.java, LongNullAdapter())

            .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerNullAdapter())
            .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleNullAdapter())
            .registerTypeAdapter(Long::class.javaPrimitiveType, LongNullAdapter())
            .create()
    }
}