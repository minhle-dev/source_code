package com.minhle.flickkfinal.model

import com.google.gson.annotations.SerializedName

data class YoutubeResponse(
    @SerializedName("name") val name: String,
    @SerializedName("size") val size: String,
    @SerializedName("source") val source: String,
    @SerializedName("type") val type: String
)
