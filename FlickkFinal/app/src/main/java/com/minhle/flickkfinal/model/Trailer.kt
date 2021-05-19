package com.minhle.flickkfinal.model

import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("id") val id: String,
    @SerializedName("youtube") val videos: List<YoutubeResponse>,
)
