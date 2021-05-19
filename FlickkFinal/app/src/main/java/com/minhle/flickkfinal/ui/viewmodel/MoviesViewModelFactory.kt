package com.minhle.flickkfinal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minhle.flickkfinal.data.MoviesRepository

class MoviesViewModelFactory(
    private val moviesRepository: MoviesRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoviesViewModel(moviesRepository) as T
        }

        throw IllegalArgumentException("Unable construct viewModel")
    }

}