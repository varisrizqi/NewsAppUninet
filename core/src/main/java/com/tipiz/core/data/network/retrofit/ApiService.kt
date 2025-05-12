package com.tipiz.core.data.network.retrofit

import com.tipiz.core.data.network.data.headlinenews.HeadLinesNewsResponse
import com.tipiz.core.data.network.data.source.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("sources")
    suspend fun fetchSources(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String? = null,
        @Query("language") language: String? = "en",
        @Query("country") country: String? = null
    ): SourcesResponse

    @GET("top-headlines")
    suspend fun fetchNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") q: String,
        @Query("page") page: Int
    ): HeadLinesNewsResponse
}