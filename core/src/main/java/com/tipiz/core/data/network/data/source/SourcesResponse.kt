package com.tipiz.core.data.network.data.source


import com.google.gson.annotations.SerializedName

data class SourcesResponse(
    @SerializedName("sources")
    val sources: List<Source?>? = null,
    @SerializedName("status")
    val status: String? = null
)

