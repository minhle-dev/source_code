package com.minhle.flickkfinal

import com.minhle.flickkfinal.api.FlickkService
import com.minhle.flickkfinal.data.MoviesRepository
import com.minhle.flickkfinal.ui.viewmodel.MoviesViewModelFactory

object Injection {
    private fun moviesRepository() =
        MoviesRepository(FlickkService.create())

    fun provideMoviesViewModelFactory() = MoviesViewModelFactory(
        moviesRepository()
    )
}