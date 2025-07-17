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
 * File: LongNullAdapter
 * Author: Victor
 * Date: 2022/3/1 12:06
 * Description: 
 * -----------------------------------------------------------------
 */

class LongNullAdapter: TypeAdapter<Long>() {

    override fun write(writer: JsonWriter?, value: Long?) {
        if (value == null) {
            writer?.nullValue()
            return
        }
        writer?.value(value)
    }

    override fun read(reader: JsonReader?): Long? {
        if (reader?.peek() === JsonToken.NULL) {
            reader?.nextNull()
            return 0
        }
        val stringValue = reader?.nextString() ?: ""
        if (TextUtils.isEmpty(stringValue)) return null
        return try {
            stringValue.toLong()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            null
        }
    }
}