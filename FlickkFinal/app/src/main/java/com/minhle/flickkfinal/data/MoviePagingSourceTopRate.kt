package com.minhle.flickkfinal.data

import androidx.paging.PagingSource
import com.minhle.flickkfinal.api.FlickkService
import com.minhle.flickkfinal.data.MoviesRepository.Companion.NETWORK_PAGE_SIZE
import com.minhle.flickkfinal.model.GetMoviesResponse
import com.minhle.flickkfinal.model.Movie
import com.minhle.flickkfinal.utils.Constants.Companion.API_KEY
import okio.IOException
import retrofit2.HttpException


private const val MOVIE_STARTING_PAGE_INDEX = 1

class MoviePagingSourceTopRate(private val api: FlickkService) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
        return try {
            val movies = api.getTopRated(API_KEY, position)
            LoadResult.Page(
                data = movies.movies,
                prevKey = if (position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.movies.isEmpty()) null else position + (params.loadSize / NETWORK_PAGE_SIZE)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }


}