package org.victor.khttp.library.util

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Log
import org.jsoup.Jsoup
import org.victor.khttp.library.data.FormImage
import java.io.*
import java.net.*
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpUtil.kt
 * Author: Victor
 * Date: 2018/8/22 17:01
 * Description: http请求工具类
 * -----------------------------------------------------------------
 */
class HttpUtil {
    companion object {
        private val TAG = "HttpUtil"
        private val CONNECT_TIME_OUT = 10000
        private val READ_TIME_OUT = 15000
        private val UPLOAD_TIME_OUT = 10 * 10000000
        private val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63"
        private val ENCODE: String = "utf-8"

        @TargetApi(Build.VERSION_CODES.KITKAT)
        fun post(requestUrl: String?, header:HashMap<String,String>?,parms: String?): String {
            var result:String = ""
            try {
                var url = URL(requestUrl)
                var conn:HttpURLConnection = url.openConnection() as HttpURLConnection

                conn.setConnectTimeout(CONNECT_TIME_OUT)
                conn.setReadTimeout(READ_TIME_OUT)
                conn.setDoInput(true)
                conn.setDoOutput(true)

                //add reuqest header
                conn.setRequestMethod("POST")
                conn.setRequestProperty("User-Agent", USER_AGENT)
                conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
                conn.setRequestProperty("Accept-Charset", ENCODE)
                conn.setRequestProperty("Content-type", "application/json")

                if (header != null) {
                    for ((k,v) in header) {
                        conn.setRequestProperty(k,v)
                    }
                }

                var dos = DataOutputStream(conn.getOutputStream())

                dos.write(parms?.toByteArray(StandardCharsets.UTF_8))

                dos.flush()
                dos.close()

                var responseCode:Int = conn.getResponseCode()

                Log.e(TAG,"post-responseUrl = ${requestUrl}")
                Log.e(TAG,"post-responseCode = ${responseCode}")

                var inputStream: InputStream = conn.getInputStream()
                var bos = ByteArrayOutputStream()
                var buffer = ByteArray(1024)
                var size = inputStream.read(buffer)
                while (size != -1) {
                    bos.write(buffer, 0, size)
                    size = inputStream.read(buffer)
                }
                result = String(bos.toByteArray(), Charset.forName(ENCODE))
                inputStream.close()
                bos.close()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            } catch (e: ProtocolException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Log.e(TAG,"post-responseData = ${result}")
            return result
        }

        @Throws(SocketTimeoutException::class)
        fun get(requestUrl: String?): String {
            var result = ""
            try {
                var url = URL(requestUrl)
                var conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.setConnectTimeout(CONNECT_TIME_OUT)
                conn.setReadTimeout(READ_TIME_OUT)
                // optional default is GET
                conn.setRequestMethod("GET")

                //add request header
                conn.setRequestProperty("User-Agent", USER_AGENT)

                var responseCode = conn.getResponseCode()

                Log.e(TAG,"get-responseUrl = ${requestUrl}")
                Log.e(TAG,"get-responseCode = ${responseCode}")

                val inputStream:InputStream = conn.getInputStream()
                val bos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var size = inputStream.read(buffer)
                while (size != -1) {
                    bos.write(buffer, 0, size)
                    size = inputStream.read(buffer)
                }
                result = String(bos.toByteArray(), Charset.forName(ENCODE))
                inputStream.close()
                bos.close()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Log.e(TAG,"get-responseData = ${result}")
            return result
        }
        @Throws(SocketTimeoutException::class)
        fun upload(requestUrl:String?, header: HashMap<String,String>?,formImage: FormImage?): String ? {
            var result:String? = null
            val CONTENT_TYPE = "multipart/form-data" //内容类型
            val BOUNDARY = UUID.randomUUID().toString() //边界标识 随机生成
            val PREFIX = "--"
            val LINE_END = "\r\n"
            try {
                var url = URL(requestUrl)
                var conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.setReadTimeout(UPLOAD_TIME_OUT)
                conn.setConnectTimeout(UPLOAD_TIME_OUT)
                conn.setDoInput(true) //允许输入流
                conn.setDoOutput(true) //允许输出流
                conn.setUseCaches(false) //不允许使用缓存
                conn.setRequestMethod("POST") //请求方式
                conn.setRequestProperty("Charset", ENCODE)
                //设置编码
                conn.setRequestProperty("connection", "keep-alive")
                conn.setRequestProperty("Content-Type", "$CONTENT_TYPE;boundary=$BOUNDARY")

                if (header != null) {
                    for ((k,v) in header) {
                        conn.setRequestProperty(k,v)
                    }
                }

                /** * 当文件不为空，把文件包装并且上传  */
                val outputSteam = conn.getOutputStream()
                val dos = DataOutputStream(outputSteam)
                val sb = StringBuffer()
                sb.append(PREFIX)
                sb.append(BOUNDARY)
                sb.append(LINE_END)
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */

                var file:File = File(formImage?.imgPath)
                sb.append("Content-Disposition: form-data; name=\"${formImage!!.name}\"; filename=\"" + file!!.getName() + "\"" + LINE_END)

//                sb.append("Content-Type: application/octet-stream; charset=${ENCODE}$LINE_END")
                sb.append("Content-Type: ${formImage?.mime}; charset=${ENCODE}$LINE_END")
                sb.append(LINE_END)
                dos.write(sb.toString().toByteArray())

                dos.write(getImageBytes(BitmapFactory.decodeFile(formImage?.imgPath)))

//                inputStream.close()
                dos.write(LINE_END.toByteArray())
                val end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).toByteArray()
                dos.write(end_data)
                dos.flush()
                /**
                 * 获取响应码 200=成功
                 * 当响应成功，获取响应的流
                 */
                val responseCode = conn.getResponseCode()
                var inputStream: InputStream = conn.getInputStream()
                val bos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var size = inputStream.read(buffer)
                while (size != -1) {
                    bos.write(buffer, 0, size)
                    size = inputStream.read(buffer)
                }
                result = String(bos.toByteArray(), Charset.forName(ENCODE))


                Log.e(TAG,"upload-responseUrl = ${requestUrl}")
                Log.e(TAG,"upload-responseCode = ${responseCode}")
                Log.e(TAG,"upload-responseData = ${result}")
                dos.close()
                inputStream.close()
                bos.close()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return result
        }

        @Throws(SocketTimeoutException::class)
        fun jsoup (requestUrl: String?): String {
            var document = Jsoup.connect(requestUrl)
                    .userAgent(USER_AGENT)
                    .timeout(CONNECT_TIME_OUT).get();
            return document.toString()
        }
        /**
         * 将图片转换为数组
         * @param bmp
         * @return
         */
        fun getImageBytes(bmp: Bitmap?): ByteArray? {
            if (bmp == null) return null
            val baos = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            return baos.toByteArray()
        }


        /**
         * 检测网络是否可用
         * @param context
         * @return
         */
        @SuppressLint("MissingPermission")
        fun isNetEnable(context: Context): Boolean {
            val connManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkinfo = connManager.activeNetworkInfo
            return if (networkinfo == null || !networkinfo.isAvailable) {
                false
            } else true

        }

        /**
         * 检测网络是否经过了代理，防止网络抓包
         * @param context
         * @return
         */
        fun isWifiProxy(context: Context): Boolean {
            var IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
            var proxyAddress:String ;
            var proxyPort:Int = -1 ;
            if (IS_ICS_OR_LATER) {
                proxyAddress = System.getProperty("http.proxyHost");
                var portStr = System.getProperty("http.proxyPort");
                proxyPort = Integer.parseInt(portStr);
            } else {
                proxyAddress = android.net.Proxy.getHost(context);
                proxyPort = android.net.Proxy.getPort(context);
            }
            return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
        }
    }

}