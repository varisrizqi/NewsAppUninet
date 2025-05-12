package com.tipiz.core.data.network.retrofit

import com.tipiz.core.BuildConfig.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader(API_KEY, API_KEY)
            .build()
        return chain.proceed(newRequest)
    }

}