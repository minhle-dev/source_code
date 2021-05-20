    package com.minhle.flickkfinal.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textview.MaterialTextView
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.model.Movie
import com.smarteist.autoimageslider.SliderViewAdapter


class SliderAdapter(private val context: Context) :
    SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {
    private var mSliderItems: MutableList<Movie> = ArrayList()

    fun renewItems(sliderItems: MutableList<Movie>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: Movie) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_slider, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val sliderItem: Movie = mSliderItems[position]

        Glide.with(viewHolder.itemView)
            .load("https://image.tmdb.org/t/p/w500${sliderItem.backdropPath}")
            .apply(RequestOptions.bitmapTransform(RoundedCorners(1  )))
            .thumbnail(0.1f)
            .error(R.drawable.broken_image)
            .fitCenter()
            .into(viewHolder.imageViewBackground)

        viewHolder.itemView.setOnClickListener {
            onItemClickListener?.let { it(sliderItem) }
        }

        viewHolder.txtTitleSlider.text = sliderItem.title
    }

    private var onItemClickListener: ((Movie) -> Unit)? = null
    fun setOnclickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    override fun getCount(): Int = mSliderItems.size


    inner class SliderAdapterVH(itemView: View) :
        ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
        var txtTitleSlider: MaterialTextView = itemView.findViewById(R.id.txt_movie_title_slider)

    }
}