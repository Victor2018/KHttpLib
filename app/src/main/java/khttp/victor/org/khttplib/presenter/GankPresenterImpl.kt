package khttp.victor.org.khttplib.presenter

import khttp.victor.org.khttplib.view.GankView
import org.victor.khttp.library.annotation.HttpParms
import org.victor.khttp.library.data.GankReq
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.inject.HttpInject
import org.victor.khttp.library.module.HttpRequest
import org.victor.khttp.library.presenter.impl.BasePresenterImpl

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankPresenterImpl.kt
 * Author: Victor
 * Date: 2018/8/24 13:49
 * Description: 
 * -----------------------------------------------------------------
 */
class GankPresenterImpl(var gankView: GankView?): BasePresenterImpl() {

    override fun onComplete(data: Any?, msg: String) {
        gankView?.OnGank(data,msg)
    }

    override fun detachView() {
        gankView = null
    }

    @HttpParms (method = Request.GET,responseCls = GankReq::class)
    override fun sendRequest(url: String, header: HashMap<String, String>?, parms: String?) {
        HttpInject.inject(this);
        super.sendRequest(url, header, parms)
    }
}