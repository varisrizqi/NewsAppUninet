package com.tipiz.core.domain.model

data class DataHeadLinesNews (
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: DataSource? = DataSource(),
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = ""
)