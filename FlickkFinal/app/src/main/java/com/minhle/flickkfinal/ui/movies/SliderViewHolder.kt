package com.minhle.flickkfinal.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.databinding.ItemMovieBinding
import com.minhle.flickkfinal.databinding.ItemSliderBinding
import com.minhle.flickkfinal.model.Movie

class SliderViewHolder(
    private val binding: ItemSliderBinding,
    private val openMoviesDetail: (Movie) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {
    private var movie: Movie? = null

    init {
        binding.layoutItemSlider.setOnClickListener {
            movie?.let {
                openMoviesDetail(it)
            }
        }
    }

    fun onBind(movie: Movie) {
        this.movie = movie

        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w700${movie.backdropPath}")
            .apply(RequestOptions.bitmapTransform(RoundedCorners(1  )))
            .thumbnail(0.1f)
            .error(R.drawable.broken_image)
            .into(binding.ivAutoImageSlider)


    }

    companion object {
        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            openMoviesDetail: (Movie) -> Unit
        ): SliderViewHolder {
            val binding: ItemSliderBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_slider, parent, false)
            return SliderViewHolder(binding, openMoviesDetail)
        }
    }
}