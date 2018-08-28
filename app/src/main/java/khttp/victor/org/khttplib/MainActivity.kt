package khttp.victor.org.khttplib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.fastjson.JSON
import khttp.victor.org.khttplib.presenter.GankPresenterImpl
import khttp.victor.org.khttplib.presenter.PhoneCodePresenterImpl
import khttp.victor.org.khttplib.view.PhoneCodeView
import khttp.victor.org.khttplib.view.GankView
import kotlinx.android.synthetic.main.activity_main.*
import org.victor.khttp.library.module.HttpRequest
import org.victor.khttp.library.util.Constant
import android.app.Dialog
import android.app.ProgressDialog
import android.view.View
import khttp.victor.org.khttplib.data.PhoneCodeParm
import khttp.victor.org.khttplib.data.PhoneCodeRequest
import khttp.victor.org.khttplib.presenter.UploadPresenterImpl
import khttp.victor.org.khttplib.view.UploadView
import org.victor.khttp.library.data.FormImage

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MainActivity.kt
 * Author: Victor
 * Date: 2018/8/24 13:51
 * Description:
 * -----------------------------------------------------------------
 */

class MainActivity : AppCompatActivity(),View.OnClickListener,GankView, PhoneCodeView,UploadView {
    var TAG: String = "MainActivity"
    var gankPresenter: GankPresenterImpl? = null
    var phoneCodePresenter: PhoneCodePresenterImpl? = null
    var uploadPresenter: UploadPresenterImpl? = null
    var loadingDialog: Dialog? = null

    override fun OnGank(data: Any?, error: String) {
        loadingDialog?.dismiss();
        mTvResponse.setText(JSON.toJSONString(data))
    }

    override fun OnPhoneCode(data: Any?, error: String) {
        loadingDialog?.dismiss();
        mTvResponse.setText(JSON.toJSONString(data))
    }

    override fun OnUpload(data: Any?, error: String) {
        loadingDialog?.dismiss();
        mTvResponse.setText(JSON.toJSONString(data))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    fun initialize () {
        loadingDialog = ProgressDialog(this);
        loadingDialog?.setTitle("加载中...");
        gankPresenter = GankPresenterImpl(this)
        phoneCodePresenter = PhoneCodePresenterImpl(this)
        uploadPresenter = UploadPresenterImpl(this)

        mBtnGet.setOnClickListener(this)
        mBtnPost.setOnClickListener(this)
        mBtnUploadImg.setOnClickListener(this)
    }

    fun sendGankRequest () {
        loadingDialog?.show();
        gankPresenter?.sendRequest(Constant.GANK_URL,null,null)
    }

    fun sendPhoneCode() {
        loadingDialog?.show();
        val request = PhoneCodeRequest()
        request.app = "com.fungo.hotchat"
        request.channel = "360"
        request.model = "Lenovo A7600-m"
        request.nettype = 0
        request.term = 0
        request.ts = System.currentTimeMillis()
        request.udid = "aad36635a567008985006ea9741b7823"
        request.uid = 0
        request.version = 10
        request.version_name = "1.5.0"

        val data = PhoneCodeParm()
        data.phone = "18813938924"
        data.type = 0

        request.data = data

        phoneCodePresenter?.sendRequest(Constant.PHONE_CODE_URL, null, JSON.toJSONString(request))
    }

    fun sendUploadRequest () {
        loadingDialog?.show();
        val img = FormImage()
        img.imgPath = "/sdcard/test.jpeg"
        img.mime = FormImage.getPictureType(img.imgPath)
        img.name = "file"
        uploadPresenter?.sendRequest(Constant.UPLOAD_IMG_URL, getHttpHeaderParm(), JSON.toJSONString(img))
    }

    fun getHttpHeaderParm(): HashMap<String, String> {
        val header = java.util.HashMap<String, String>()
        header["X-token"] = "6f7ac60289382df0309e5c00c43053f5"
        header["X-version"] = "1.0.1"
        header["X-deviceType"] = "phone"
        header.put("X-mac", "64bc0c50c437");
        header["X-areaCode"] = ""//
        return header
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.mBtnGet ->{
                sendGankRequest()
            }
            R.id.mBtnPost ->{
                sendPhoneCode()
            }
            R.id.mBtnUploadImg ->{
                sendUploadRequest()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        HttpRequest.instance.onDestroy()
    }
}
