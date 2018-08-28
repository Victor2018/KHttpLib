package org.victor.khttp.library.data

import android.graphics.BitmapFactory
import android.text.TextUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FormImage.kt
 * Author: Victor
 * Date: 2018/8/28 10:35
 * Description: 上传图片参数实体
 * -----------------------------------------------------------------
 */
class FormImage {
    var name:String? = null
    var imgPath:String? = null
    var mime:String? = null

    companion object {
        fun getPictureType (path:String?):String {
            var type = "image/jpeg"
            if (TextUtils.isEmpty(path)) {
                return type
            }
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)
            type = options.outMimeType
            return type
        }
    }

}