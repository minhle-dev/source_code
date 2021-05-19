package com.minhle.flickkfinal.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.databinding.ItemMovieBinding
import com.minhle.flickkfinal.databinding.ItemReviewBinding
import com.minhle.flickkfinal.model.Movie
import com.minhle.flickkfinal.model.Review

class ReviewViewHolder(
    private val binding: ItemReviewBinding,
) :
    RecyclerView.ViewHolder(binding.root) {
    private var review: Review? = null

    fun onBind(review: Review) {
        this.review = review

        binding.txtAuthor.text = review.author
        binding.txtContent.text = review.content


    }

    companion object {
        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
        ): ReviewViewHolder {
            val binding: ItemReviewBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_review, parent, false)
            return ReviewViewHolder(binding)
        }
    }
}