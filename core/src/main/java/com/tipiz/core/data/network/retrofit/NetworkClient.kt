package com.tipiz.core.data.network.retrofit

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tipiz.core.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient (
    val requestInterceptor: RequestInterceptor,
    val chuckerInterceptor: ChuckerInterceptor
) {

    inline fun <reified I> create(): I {


        val okHttpClient =  OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(requestInterceptor)

            .connectTimeout(timeout = 120, TimeUnit.SECONDS)
            .readTimeout(timeout = 120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(I::class.java)
    }
}