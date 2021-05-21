package com.minhle.flickkfinal.ui.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.model.Movie


class MoreMoviesAdapter() :
    PagingDataAdapter<Movie, MoreMoviesAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageMovie: ImageView = itemView.findViewById(R.id.movie_image)
        private val txtTitle: TextView = itemView.findViewById(R.id.txt_movie_title)
        private val movieRating: RatingBar = itemView.findViewById(R.id.movie_rating)


        fun onBind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w300${movie.posterPath}")
                .apply(RequestOptions.bitmapTransform(RoundedCorners(1)))
                .thumbnail(0.1f)
                .error(R.drawable.broken_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageMovie)

            txtTitle.text = movie.title
            movieRating.rating = (movie.rating) / 2

            itemView.setOnClickListener {
                onItemClickListener?.let { it(movie) }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }


            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null
    fun setOnclickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

}






