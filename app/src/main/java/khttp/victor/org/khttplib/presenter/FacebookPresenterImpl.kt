package khttp.victor.org.khttplib.presenter

import khttp.victor.org.khttplib.util.FacebookParser
import khttp.victor.org.khttplib.view.FacebookView
import org.victor.khttp.library.annotation.HttpParms
import org.victor.khttp.library.data.Request
import org.victor.khttp.library.inject.HttpInject
import org.victor.khttp.library.presenter.impl.BasePresenterImpl

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by longtv, All rights reserved.
 * -----------------------------------------------------------------
 * File: VimeoPresenterImpl.java
 * Author: Victor
 * Date: 2018/10/24 14:31
 * Description: 
 * -----------------------------------------------------------------
 */
class FacebookPresenterImpl(var facebookView: FacebookView?): BasePresenterImpl() {
    override fun onComplete(data: Any?, msg: String) {
        var facebookReq = FacebookParser.parseFacebook(data?.toString())
        facebookView?.OnFacebook(facebookReq,msg)
    }

    override fun detachView() {
        facebookView = null
    }

    @HttpParms (method = Request.JSOUP,responseCls = String::class)
    override fun sendRequest(url: String, header: HashMap<String, String>?, parms: String?) {
        HttpInject.inject(this);
        super.sendRequest(url, header, parms)
    }
}