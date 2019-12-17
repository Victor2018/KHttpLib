package khttp.victor.org.khttplib.presenter

import khttp.victor.org.khttplib.data.UploadReq
import khttp.victor.org.khttplib.view.UploadView
import org.victor.khttp.library.annotation.HttpParms
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.inject.HttpInject
import org.victor.khttp.library.presenter.impl.BaseUploadPresenterImpl

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: UploadPresenterImpl.kt
 * Author: Victor
 * Date: 2018/8/28 10:49
 * Description: 
 * -----------------------------------------------------------------
 */
class UploadPresenterImpl (var uploadView: UploadView?) : BaseUploadPresenterImpl() {
    override fun onComplete(data: Any?, msg: String?) {
        uploadView?.OnUpload(data,msg)
    }
    override fun detachView() {
        uploadView = null
    }
    @HttpParms(method = Request.POST,responseCls = UploadReq::class)
    override fun sendRequest(url: String, header: HashMap<String, String>?, parms: String?) {
        HttpInject.inject(this);
        super.sendRequest(url, header, parms)
    }
}