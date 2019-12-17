package org.victor.khttp.library.module

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.text.TextUtils
import android.util.Log
import org.victor.khttp.library.util.Constant
import org.victor.khttp.library.util.HttpUtil
import java.util.HashMap
import com.alibaba.fastjson.JSON
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.victor.khttp.library.data.FormImage
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.presenter.OnHttpListener
import org.victor.khttp.library.util.MainHandler
import kotlin.reflect.KClass
import org.json.JSONTokener
import java.lang.Exception


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpRequest.kt
 * Author: Victor
 * Date: 2018/8/22 15:47
 * Description: 使用HandlerThread处理http请求核心模块
 * -----------------------------------------------------------------
 */
class HttpRequest () {
    private var TAG = "HttpRequest"
    val ARRAY_LIST_KEY = "ARRAY_LIST_KEY"
    private object Holder { val instance = HttpRequest()}
    private var mRequestHandler: Handler? = null
    private var mRequestHandlerThread: HandlerThread? = null
    private var requestUrl: String? = null
    private var headers: HashMap<String,String>? = null
    var requests= HashMap<String,Request>()
    private var parms: String? = null
    private var formImage: FormImage? = null

    private var requestMethod: Int = Request.GET
    private var responseCls: KClass<Any>?= null




    companion object {
        val  instance: HttpRequest by lazy { HttpRequest.Holder.instance }
    }

    //由于primary constructor不能包含任何代码，因此使用 init 代码块对其初始化，同时可以在初始化代码块中使用构造函数的参数
    init {
        startRequestTask()
    }

    fun setRequestMethod(method:Int) {
        requestMethod = method
    }

    fun setResponseCls(cls: KClass<Any>) {
        responseCls = cls
    }

    fun startRequestTask() {
        mRequestHandlerThread = HandlerThread("HttpRequestTask")
        mRequestHandlerThread?.start()
        mRequestHandler = object : Handler(mRequestHandlerThread?.looper) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    Constant.SEND_GET_REQUEST -> {
                        onReponse(requestUrl,HttpUtil.get(requestUrl))
                    }
                    Constant.SEND_POST_REQUEST -> {
                        onReponse(requestUrl,HttpUtil.post(requestUrl,headers,parms))
                    }
                    Constant.MULTIPART_UPLOAD_REQUEST -> {
                        onReponse(requestUrl,HttpUtil.upload(requestUrl,headers,formImage))
                    }
                }
            }
        }
    }

    fun sendRequest (url:String, headers: HashMap<String,String>?,parms:String?,formImage: FormImage?,listener :OnHttpListener?) {
        requests?.put(url, Request(requestMethod,responseCls,listener))
        if (requestMethod == Request.GET) {
            sendGetRequest(url)
        } else if (requestMethod == Request.POST) {
            sendPostRequest(url,headers,parms)
        }  else if (requestMethod == Request.UPLOAD) {
            sendMultipartUploadRequest(url,headers,formImage)
        }
    }

    fun sendGetRequest(url:String) {
        Log.e(TAG,"sendGetRequest-requestUrl = ${url}")
        requestUrl = url;
        var msg = mRequestHandler?.obtainMessage(Constant.SEND_GET_REQUEST)
        mRequestHandler?.sendMessage(msg);
    }

    fun sendPostRequest(url:String, headers: HashMap<String,String>?,parms:String?) {
        Log.e(TAG,"sendPostRequest-requestUrl = ${url}")
        Log.e(TAG,"sendPostRequest-headers = ${headers.toString()}")
        Log.e(TAG,"sendPostRequest-parms = ${parms}")
        requestUrl = url;
        this.headers = headers
        this.parms = parms
        val msg = mRequestHandler?.obtainMessage(Constant.SEND_POST_REQUEST)
        mRequestHandler?.sendMessage(msg)
    }

    fun sendMultipartUploadRequest (url:String, headers: HashMap<String,String>?,formImage: FormImage?) {
        Log.e(TAG,"sendMultipartUploadRequest-requestUrl = ${url}")
        Log.e(TAG,"sendMultipartUploadRequest-headers = ${headers.toString()}")
        Log.e(TAG,"sendMultipartUploadRequest-parms = ${JSON.toJSONString(formImage)}")
        requestUrl = url
        this.headers = headers
        this.formImage = formImage
        var msg = mRequestHandler?.obtainMessage(Constant.MULTIPART_UPLOAD_REQUEST)
        mRequestHandler?.sendMessage(msg);
    }

    fun onDestroy() {
        mRequestHandlerThread?.quit()
        mRequestHandlerThread = null
    }

    inline fun <reified T: Any> parseObject(text: String?, clazz: Class<T>?): T? {
        try {
            return JSON.parseObject(text, clazz)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T: Any> parseArray(text: String?, clazz: Class<T>?): List<T>? {
        try {
            return JSON.parseArray(text, clazz)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun parseReponse (url:String?, result: String?): Any? {
        var reponse:Any? = null
        try {
            if (requests.get(url)?.responseCls!!.toString().contains("String")) {
                Log.e(TAG,"reponse data is String")
                if (TextUtils.isEmpty(result)) {
                    return null
                }
                return result
            }
            val json = JSONTokener(result).nextValue()
            if (json is JSONObject) {
                Log.e(TAG,"reponse data is JSONObject")
//            reponse  = parseObject(result,responseCls!!.javaObjectType)
                reponse  = parseObject(result,requests.get(url)?.responseCls!!.java)
            } else if (json is JSONArray) {
                Log.e(TAG,"reponse data is JSONArray")
                reponse = parseArray(result,requests.get(url)?.responseCls!!.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return reponse
    }

    fun onReponse(url:String?,result: String?) {
        MainHandler.runMainThread {
            var reponse:Any? = parseReponse(url,result)
            Log.e(TAG,"onReponse-reponse = $reponse")
            requests.get(url)?.listener?.onComplete(reponse,result!!)
            requests.remove(url)
        }
    }

}