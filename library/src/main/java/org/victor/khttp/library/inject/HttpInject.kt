package org.victor.khttp.library.inject

import org.victor.khttp.library.annotation.HttpParms
import org.victor.khttp.library.module.HttpRequest
import kotlin.reflect.KClass


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpInject.kt
 * Author: Victor
 * Date: 2018/8/24 16:14
 * Description: 解析http参数注解
 * -----------------------------------------------------------------
 */
class HttpInject(var obj: Any) {
    companion object {
        fun inject(obj: Any) {
            val cls = obj.javaClass
            val methods = cls.declaredMethods

            for (method in methods) {
                try {
                    val httpParms = method.annotations
                    for (anim in httpParms) {
                        if (anim is HttpParms) {
                            HttpRequest.instance.setRequestMethod(anim.method)
                            HttpRequest.instance.setResponseCls(anim.responseCls as KClass<Any>)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}