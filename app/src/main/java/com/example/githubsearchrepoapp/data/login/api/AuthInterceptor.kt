package com.example.githubsearchrepoapp.data.login.api

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        //add access token (同时确保外部没有添加自定义的Authorization才去添加)
        if (request.header("Authorization") == null && token != null && !TextUtils.isEmpty(token?.trim())) {
            val auth = if (token?.startsWith("Basic") == true) token else "token $token"
            request = request.newBuilder()
                .addHeader("Authorization", auth)
                .build()
        }

        return chain.proceed(request)
    }

    fun setToken(token: String?) {
        this.token = token
    }

}