package com.minhle.flickkfinal.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.minhle.flickkfinal.api.FlickkService
import com.minhle.flickkfinal.model.Movie
import com.minhle.flickkfinal.utils.Constants.Companion.API_KEY
import kotlinx.coroutines.flow.Flow

class MoviesRepository(private val service: FlickkService) {

    suspend fun getNowMovies(page: Int) = service.getNowMovies(API_KEY, page)

    suspend fun getSearchMovie(query: String, page: Int) = service.getSearchMovie(API_KEY, query,page)

    suspend fun getPopularMovies(page: Int) = service.getPopularMovies(API_KEY, page)

    suspend fun getTrailerMovies(id: String) = service.getTrailerMovies(id, API_KEY)

    suspend fun getTopRated(page: Int) = service.getTopRated(API_KEY, page)

    suspend fun getReviewMovies(movie_id: String) =
        service.getReviewMovies(movie_id = movie_id, API_KEY)

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }


    fun getMoviesFromApi(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSource(service) }
    ).flow

    fun getMoviesTopRate(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSourceTopRate(service) }
    ).flow

    fun getMoviesPopular(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSourcePopular(service) }
    ).flow


}