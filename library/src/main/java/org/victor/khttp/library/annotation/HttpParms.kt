package org.victor.khttp.library.annotation

import kotlin.reflect.KClass

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpParms.kt
 * Author: Victor
 * Date: 2018/8/24 15:06
 * Description: 自定义请求参数注解
 * -----------------------------------------------------------------
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpParms(val method:Int,val responseCls: KClass<out  Any>)