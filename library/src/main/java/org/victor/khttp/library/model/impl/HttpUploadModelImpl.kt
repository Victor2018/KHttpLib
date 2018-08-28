package org.victor.khttp.library.model.impl

import com.alibaba.fastjson.JSON
import org.json.JSONException
import org.victor.khttp.library.data.FormImage
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.model.HttpModel
import org.victor.khttp.library.module.HttpRequest
import org.victor.khttp.library.presenter.OnHttpListener

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpUploadModelImpl.kt
 * Author: Victor
 * Date: 2018/8/28 9:56
 * Description: 上传图片http请求model实现
 * -----------------------------------------------------------------
 */
class HttpUploadModelImpl : HttpModel {
    var TAG = "HttpUploadModelImpl"
    override fun sendReuqest(url: String, header: HashMap<String, String>?, parms: String?, listener: OnHttpListener?) {
        HttpRequest.instance.setRequestMethod(Request.UPLOAD)
        HttpRequest.instance.sendRequest(url,header,parms,parseObject(parms,FormImage::class.java),listener)
    }

    inline fun <reified T: Any> parseObject(text: String?, clazz: Class<T>?): T? {
        try {
            return JSON.parseObject(text, clazz)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }
}