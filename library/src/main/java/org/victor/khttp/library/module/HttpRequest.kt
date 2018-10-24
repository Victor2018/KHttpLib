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
import org.json.JSONException
import org.victor.khttp.library.data.FormImage
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.presenter.OnHttpListener
import org.victor.khttp.library.util.MainHandler
import kotlin.reflect.KClass

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
    private object Holder { val instance = HttpRequest()}
    private var mRequestHandler: Handler? = null
    private var mRequestHandlerThread: HandlerThread? = null
    private var requestUrl: String? = null
    private var headers: HashMap<String,String>? = null
    private var parms: String? = null
    private var formImage: FormImage? = null

    private var listener : OnHttpListener? = null
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
                        var result: String? = HttpUtil.get(requestUrl)
//                        var reponse  = parseObject(result,responseCls!!.javaObjectType)
                        var reponse:Any? = result
                        Log.e("HttpRequest","responseCls = ${responseCls}")
                        if (!responseCls!!.toString().contains("String")!!) {
                            reponse  = parseObject(result,responseCls!!.java)
                        }
                        MainHandler.runMainThread {
                            listener?.onComplete(reponse,"success")
                        }
                    }
                    Constant.SEND_POST_REQUEST -> {
                        val result: String? = HttpUtil.post(requestUrl,headers,parms)
                        var reponse:Any? = result
                        Log.e("HttpRequest","responseCls = ${responseCls}")
                        if (!responseCls!!.toString().contains("String")!!) {
                            reponse  = parseObject(result,responseCls!!.java)
                        }
                        MainHandler.runMainThread {
                            listener?.onComplete(reponse,"success")
                        }
                    }
                    Constant.MULTIPART_UPLOAD_REQUEST -> {
                        val result: String? = HttpUtil.upload(requestUrl,headers,formImage)
                        var reponse:Any? = result
                        Log.e("HttpRequest","responseCls = ${responseCls}")
                        if (!responseCls!!.toString().contains("String")!!) {
                            reponse  = parseObject(result,responseCls!!.java)
                        }
                        MainHandler.runMainThread {
                            listener?.onComplete(reponse,"success")
                        }
                    }
                    Constant.JSOUP_REQUEST -> {
                        val result: String? = HttpUtil.jsoup(requestUrl)
                        MainHandler.runMainThread {
                            listener?.onComplete(result,"success")
                        }
                    }
                }
            }
        }
    }

    fun sendPostRequest(url:String, headers: HashMap<String,String>?,parms:String?,listener :OnHttpListener?) {
        Log.e(TAG,"sendPostRequest-requestUrl = ${url}")
        Log.e(TAG,"sendPostRequest-headers = ${headers.toString()}")
        Log.e(TAG,"sendPostRequest-parms = ${parms}")
        requestUrl = url;
        this.headers = headers
        this.parms = parms
        this.listener = listener
        val msg = mRequestHandler?.obtainMessage(Constant.SEND_POST_REQUEST)
        mRequestHandler?.sendMessage(msg)
    }

    fun sendGetRequest(url:String,listener :OnHttpListener?) {
        Log.e(TAG,"sendGetRequest-requestUrl = ${url}")
        requestUrl = url;
        this.listener = listener
        var msg = mRequestHandler?.obtainMessage(Constant.SEND_GET_REQUEST)
        mRequestHandler?.sendMessage(msg);
    }
    fun sendJsoupRequest(url:String,listener :OnHttpListener?) {
        Log.e(TAG,"sendJsoupRequest-requestUrl = ${url}")
        requestUrl = url;
        this.listener = listener
        var msg = mRequestHandler?.obtainMessage(Constant.JSOUP_REQUEST)
        mRequestHandler?.sendMessage(msg);
    }

    fun sendRequest (url:String, headers: HashMap<String,String>?,parms:String?,formImage: FormImage?,listener :OnHttpListener?) {
        if (requestMethod == Request.GET) {
            sendGetRequest(url,listener)
        } else if (requestMethod == Request.POST) {
            sendPostRequest(url,headers,parms,listener)
        }  else if (requestMethod == Request.UPLOAD) {
            sendMultipartUploadRequest(url,headers,formImage,listener)
        }  else if (requestMethod == Request.JSOUP) {
            sendJsoupRequest(url,listener)
        }
    }

    fun sendMultipartUploadRequest (url:String, headers: HashMap<String,String>?,formImage: FormImage?,listener :OnHttpListener?) {
        Log.e(TAG,"sendMultipartUploadRequest-requestUrl = ${url}")
        Log.e(TAG,"sendMultipartUploadRequest-headers = ${headers.toString()}")
        Log.e(TAG,"sendMultipartUploadRequest-parms = ${JSON.toJSONString(formImage)}")
        requestUrl = url
        this.headers = headers
        this.formImage = formImage
        this.listener = listener
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

}