package org.victor.http.lib.verifier

import okhttp3.internal.tls.OkHostnameVerifier
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpHostNameVerifier
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

class HttpHostNameVerifier: HostnameVerifier {
    val TAG = "HttpHostNameVerifier"
    override fun verify(hostname: String, session: SSLSession): Boolean {
        return OkHostnameVerifier.verify("*.hok.com" ,session)
    }
}