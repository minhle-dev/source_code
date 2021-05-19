package com.minhle.flickkfinal.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.minhle.flickkfinal.data.MoviesRepository
import com.minhle.flickkfinal.model.GetMoviesResponse
import com.minhle.flickkfinal.model.Movie
import com.minhle.flickkfinal.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {



    fun getNowMovies(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(moviesRepository.getNowMovies(page)))
        } catch (ex: IOException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        }
    }

    fun getSearchMovie(query: String, page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(moviesRepository.getSearchMovie(query, page)))
        } catch (ex: IOException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        }
    }

    fun getPopularMovies(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(moviesRepository.getPopularMovies(page)))
        } catch (ex: IOException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        }
    }


    fun getTopRated(page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(moviesRepository.getTopRated(page)))
        } catch (ex: IOException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        }
    }

    fun getTrailerMovies(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(moviesRepository.getTrailerMovies(id)))
        } catch (ex: IOException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        }
    }

    fun getReviewMovies(movie_id: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(moviesRepository.getReviewMovies(movie_id)))
        } catch (ex: IOException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        }
    }

    fun getMoviesFromApi(): Flow<PagingData<Movie>> {
        return moviesRepository.getMoviesFromApi().cachedIn(viewModelScope)
    }

    fun getMoviesTopRate(): Flow<PagingData<Movie>> {
        return moviesRepository.getMoviesTopRate().cachedIn(viewModelScope)
    }

    fun getMoviesPopular(): Flow<PagingData<Movie>> {
        return moviesRepository.getMoviesPopular().cachedIn(viewModelScope)
    }


}