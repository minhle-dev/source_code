package com.minhle.flickkfinal.ui.detail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import com.minhle.flickkfinal.Injection
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.base.BaseFragment
import com.minhle.flickkfinal.databinding.FragmentDetailBinding
import com.minhle.flickkfinal.model.Movie
import com.minhle.flickkfinal.model.Trailer
import com.minhle.flickkfinal.ui.viewmodel.MoviesViewModel
import com.minhle.flickkfinal.utils.Resource
import kotlinx.android.synthetic.main.fragment_movies.*


class DetailFragment : BaseFragment() {
    private var youtubePlayerFragment: YouTubePlayerSupportFragmentX? = null

    private val controller by lazy {
        findNavController()
    }
    private var movie: Movie? = null

    private val dialogLoading by lazy {
        Dialog(requireContext())
    }
    private val binding: FragmentDetailBinding
        get() = (getViewBinding() as FragmentDetailBinding)

    private val moviesViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideMoviesViewModelFactory()
        )[MoviesViewModel::class.java]
    }
    private val reviewAdapter by lazy {
        ReviewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutId(): Int = R.layout.fragment_detail

    override fun initControls(view: View, savedInstanceState: Bundle?) {

        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(false)
        dialogLoading.setContentView(R.layout.dialog_loading)
        val movie = arguments?.get("MOVIES_DETAIL") as Movie?
        this.movie = movie
        setData()

        binding.rvReview.layoutManager = LinearLayoutManager(context)
        binding.rvReview.setHasFixedSize(true)
        binding.rvReview.adapter = reviewAdapter
        binding.rvReview.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )


        youtubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_player_view, youtubePlayerFragment!!).commit()


        getTrailerYoutube(movie!!)


        refreshDataReview()

    }


    override fun initEvents() {
        binding.swipe.setOnRefreshListener {
            refreshDataReview()
            getTrailerYoutube(movie!!)
            swipe.isRefreshing = false
        }
        binding.toolbarDetail.setNavigationOnClickListener {
            controller.popBackStack()
        }
    }


    private fun setData() {
        movie?.let {
            binding.movieTitle.text = it.title
            binding.movieOverview.text = it.overview
            binding.movieReleaseDate.text = it.releaseDate
            binding.movieRating.rating = (it.rating) / 2f
        }
    }

    private fun refreshDataReview() {
        moviesViewModel.getReviewMovies(movie!!.id).observe(viewLifecycleOwner, {
            binding.rvReview.isVisible = it is Resource.Success

            when (it) {
                is Resource.Success -> {
                    reviewAdapter.review = it.data.review
                    if (it.data.review.isEmpty()) {
                        binding.txtNoReview.isVisible = true
                    }
                    binding.swipe.isRefreshing = false
                }
                is Resource.Error -> {
                    binding.swipe.isRefreshing = false
                    it.message?.let { message ->
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show()
                    }

                }
                is Resource.Loading -> {
                    binding.swipe.isRefreshing = true

                }
            }
        })
    }


    private fun getTrailerYoutube(movie: Movie) {
        youtubePlayerFragment?.initialize(
            getString(R.string.google_api_key),
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {

                    moviesViewModel.getTrailerMovies(movie.id).observe(viewLifecycleOwner, {
                        when (it) {
                            is Resource.Success -> {
                                if (!b) {
                                    if (it.data.videos.isEmpty()) {
                                        Toast.makeText(
                                            context,
                                            "No trailer",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        binding.ivTrailer.isVisible = true
                                        binding.youtubePlayerView.isVisible = false

                                        context?.let { it1 ->
                                            Glide.with(it1)
                                                .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
                                                .apply(
                                                    RequestOptions.bitmapTransform(
                                                        RoundedCorners(
                                                            1
                                                        )
                                                    )
                                                )
                                                .thumbnail(0.1f)
                                                .error(R.drawable.broken_image)
                                                .into(binding.ivTrailer)
                                        }
                                        return@observe
                                    }
                                    if (movie.rating >= 7.0) {
                                        youTubePlayer.loadVideo(it.data.videos[0].source)
                                    } else {
                                        youTubePlayer.cueVideo(it.data.videos[0].source)
                                    }
                                }
                            }
                            is Resource.Error -> {
                                it.message.let { message ->
                                    Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show()
                                }
                            }
                            is Resource.Loading -> {
                            }
                        }

                    })

                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    Toast.makeText(context, "Load trailer failure", Toast.LENGTH_SHORT).show()
                }
            })
    }



}



