package com.minhle.flickkfinal.api


import com.minhle.flickkfinal.utils.Constants.Companion.BASE_URL
import com.minhle.flickkfinal.model.GetMoviesResponse
import com.minhle.flickkfinal.model.ReviewResponse
import com.minhle.flickkfinal.model.Trailer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FlickkService {

    @GET("movie/now_playing")
    suspend fun getNowMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): GetMoviesResponse

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): GetMoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): GetMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): GetMoviesResponse

    @GET("movie/{id}/trailers")
    suspend fun getTrailerMovies(
        @Path("id") id: String,
        @Query("api_key") apiKey: String,
    ): Trailer

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewMovies(
        @Path("movie_id") movie_id: String,
        @Query("api_key") apiKey: String,
    ): ReviewResponse


    companion object {
        fun create(): FlickkService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(FlickkService::class.java)
    }

}