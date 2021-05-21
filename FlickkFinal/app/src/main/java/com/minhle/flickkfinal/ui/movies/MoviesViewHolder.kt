package com.minhle.flickkfinal.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.databinding.ItemMovieBinding
import com.minhle.flickkfinal.model.Movie

class MoviesViewHolder(
    private val binding: ItemMovieBinding,
    private val openMoviesDetail: (Movie) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    private var movie: Movie? = null

    init {
        binding.layoutItemMovie.setOnClickListener {
            movie?.let {
                openMoviesDetail(it)
            }
        }
    }

    fun onBind(movie: Movie) {
        this.movie = movie

        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w300${movie.posterPath}")
            .apply(RequestOptions.bitmapTransform(RoundedCorners(1  )))
            .thumbnail(0.1f)
            .error(R.drawable.broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.movieImage)

        binding.txtMovieTitle.text = movie.title
        binding.movieRating.rating = (movie.rating)/2

    }

    companion object {
        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            openMoviesDetail: (Movie) -> Unit
        ): MoviesViewHolder {
            val binding: ItemMovieBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_movie, parent, false)
            return MoviesViewHolder(binding, openMoviesDetail)
        }
    }
}