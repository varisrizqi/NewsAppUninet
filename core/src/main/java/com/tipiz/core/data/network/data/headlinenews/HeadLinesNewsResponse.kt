package com.tipiz.core.data.network.data.headlinenews


import com.google.gson.annotations.SerializedName
import com.tipiz.core.data.network.data.source.Source

data class HeadLinesNewsResponse(
    @SerializedName("articles")
    val articles: List<Article?>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null
) {
    data class Article(
        @SerializedName("author")
        val author: String? = null,
        @SerializedName("content")
        val content: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("publishedAt")
        val publishedAt: String? = null,
        @SerializedName("source")
        val source: Source? = null,
        @SerializedName("title")
        val title: String? = null,
        @SerializedName("url")
        val url: String? = null,
        @SerializedName("urlToImage")
        val urlToImage: String? = null
    )
}