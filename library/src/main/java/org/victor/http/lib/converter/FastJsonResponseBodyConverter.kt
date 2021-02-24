package org.victor.http.lib.converter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.parser.Feature
import com.alibaba.fastjson.parser.ParserConfig
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.Type


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FastJsonResponseBodyConverter
 * Author: Victor
 * Date: 2021/2/24 15:53
 * Description: 
 * -----------------------------------------------------------------
 */
internal class FastJsonResponseBodyConverter<T>(
    var type: Type, var config: ParserConfig, var featureValues: Int,
    var feature: Array<out Feature>?
) : Converter<ResponseBody, T?> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {

        return try {
            JSON.parseObject(
                value.string(), type, config, featureValues,
                *feature ?: EMPTY_SERIALIZER_FEATURES
            )
        } finally {
            value.close()
        }
    }

    companion object {
        private val EMPTY_SERIALIZER_FEATURES =
            arrayOfNulls<Feature>(0)
    }

}