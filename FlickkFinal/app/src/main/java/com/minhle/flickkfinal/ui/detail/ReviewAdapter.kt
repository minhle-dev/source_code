package com.minhle.flickkfinal.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minhle.flickkfinal.model.Review

class ReviewAdapter() :
    RecyclerView.Adapter<ReviewViewHolder>() {

    var review: List<Review> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReviewViewHolder.create(inflater, parent)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.onBind(review[position])
    }

    override fun getItemCount(): Int = review.size

}