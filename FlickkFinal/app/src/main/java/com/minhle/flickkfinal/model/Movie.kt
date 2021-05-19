package com.minhle.flickkfinal.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

 open class Movie(
    @field:SerializedName("id") open val id: String,
    @field:SerializedName("title")open  val title: String,
    @field:SerializedName("overview") open val overview: String,
    @field:SerializedName("poster_path")open  val posterPath: String,
    @field:SerializedName("backdrop_path")open  val backdropPath: String,
    @field:SerializedName("vote_average")open  val rating: Float,
    @field:SerializedName("release_date")open  val releaseDate: String
):Serializable


