package org.victor.khttp.library.presenter.impl

import org.victor.khttp.library.model.HttpModel
import org.victor.khttp.library.model.impl.HttpUploadModelImpl
import org.victor.khttp.library.presenter.HttpPresenter
import org.victor.khttp.library.presenter.OnHttpListener

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseUploadPresenterImpl.kt
 * Author: Victor
 * Date: 2018/8/24 13:38
 * Description: 上传图片http请求presenter的实现
 * -----------------------------------------------------------------
 */
abstract class BaseUploadPresenterImpl: HttpPresenter,OnHttpListener {
    private var httpModelImpl:HttpModel = HttpUploadModelImpl()
    abstract fun detachView()

    override fun sendRequest (url:String, header:HashMap<String,String>?, parms: String?) {
        httpModelImpl.sendReuqest(url,header,parms,this);
    }
}