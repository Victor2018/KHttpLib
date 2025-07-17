package com.hok.lib.coremodel.http.adapter

import android.text.TextUtils
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IntTypeAdapter
 * Author: Victor
 * Date: 2022/3/1 12:06
 * Description: 
 * -----------------------------------------------------------------
 */

class IntegerNullAdapter: TypeAdapter<Number>() {

    override fun write(writer: JsonWriter?, value: Number?) {
        if (value == null) {
            writer?.nullValue()
            return
        }
        writer?.value(value)
    }

    override fun read(reader: JsonReader?): Number? {
        if (reader?.peek() === JsonToken.NULL) {
            reader?.nextNull()
            return 0
        }
        val stringValue = reader?.nextString() ?: ""
        if (TextUtils.isEmpty(stringValue)) return null
        return try {
            Integer.valueOf(stringValue)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            null
        }
    }
}