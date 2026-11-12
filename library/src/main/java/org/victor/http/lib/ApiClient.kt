package org.victor.http.lib

import android.text.TextUtils
import android.util.Log
import okhttp3.OkHttpClient
import org.victor.http.lib.adapter.NetworkResponseAdapterFactory
import org.victor.http.lib.bizparser.BusinessResponseParser
import org.victor.http.lib.data.HttpResult
import org.victor.http.lib.interceptor.BasicParamsInterceptor
import org.victor.http.lib.interceptor.LogInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val TAG = "ApiClient"


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseApiClient
 * Author: Victor
 * Date: 2021/2/24 16:29
 * Description: 使用协程进行网络请求实现的网络请求客户端
 * -----------------------------------------------------------------
 */
object ApiClient {
    var TIME_OUT:Long = 30
    var BASE_URL = ""
    var logEnable = true

    /**
     * 用于存储ApiService
     */
    private val map = mutableMapOf<Class<*>, Any>()

    /**
     * 业务响应解析器，项目统一注册一个
     */
    @Volatile
    private var responseParser: BusinessResponseParser<*, *>? = null

    private val basicParamsInterceptor by lazy {
        BasicParamsInterceptor.Builder()
//                .addHeaderParam("X-TOKEN","ff8ebca1e43feb34baf10b7b71b0ca2a98f5b2650df3b85e976639ffbbe9bacf")
//                .addQueryParam("deviceId", "12345")
            .build()
    }

    private val okHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(basicParamsInterceptor)
            .addInterceptor(LogInterceptor())

        if (!BuildConfig.DEBUG) {
//            builder.hostnameVerifier(HttpHostNameVerifier())
        }
        builder.build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(FastJsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    /**
     * 获得想要的 retrofit service
     * @param clazz    想要的 retrofit service 接口，Retrofit会代理生成对应的实体类
     * @param <T>      api service
     * @return
     */
    fun <T : Any> getApiService(apiClass: Class<T>): T {
        return getService(apiClass)
    }

    /**
     * 获取ApiService单例对象
     */
    private fun <T : Any> getService(apiClass: Class<T>): T{
        //重入锁单例避免多线程安全问题
        return if (map[apiClass] == null) {
            synchronized(ApiClient::class.java) {
                val t = retrofit.create(apiClass)
                if (map[apiClass] == null) {
                    map[apiClass] = t
                }
                t
            }
        } else {
            map[apiClass] as T
        }
    }

    /**
     * 初始化网络库：设置 baseUrl、日志开关，并注册业务响应解析器
     */
    fun <T : Any> init(
        baseUrl: String,
        parser: BusinessResponseParser<T, T>,
        logToggle: Boolean = true
    ) {
        BASE_URL = baseUrl
        responseParser = parser
        logEnable = logToggle
    }

    /**
     * 使用已注册的业务解析器解析响应体
     * @return 未注册解析器时返回 null，由调用方按成功透传处理
     */
    @Suppress("UNCHECKED_CAST")
    internal fun <T : Any> parseResponse(body: T): HttpResult<T>? {
        val parser = responseParser as? BusinessResponseParser<T, T>
        if (parser == null) {
            Log.w(TAG, "parseResponse() no parser registered for ${body.javaClass.name}, use default success")
            return null
        }

        return try {
            parser.parse(body)
        } catch (e: Exception) {
            Log.e(TAG, "parseResponse() failed, body = ${body.javaClass.name}", e)
            HttpResult.Error("0", e.localizedMessage ?: "业务响应解析异常")
        }
    }

    fun addHeader(key: String,value: String) {
        basicParamsInterceptor.headerParamsMap[key] = value
    }

    /**
     * header 是否有参数 key
     */
    fun headerHasParm (key: String): Boolean {
        var headerToken: String? = null
        if (basicParamsInterceptor.headerParamsMap.containsKey(key)) {
            headerToken = basicParamsInterceptor.headerParamsMap[key]
        }
        return TextUtils.isEmpty(headerToken)
    }

    /**
     * 获取所有header parms
     */
    fun getHttpHeaderParms(): Map<String, String> {
        return basicParamsInterceptor.headerParamsMap
    }

}