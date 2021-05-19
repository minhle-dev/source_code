package com.minhle.flickkfinal.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minhle.flickkfinal.model.Movie

class MoviesAdapter(private val openMovieDetail: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesViewHolder>() {

    var movie: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder.create(inflater, parent, openMovieDetail)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.onBind(movie[position])
    }

    override fun getItemCount(): Int = movie.size

}