package com.minhle.flickkfinal.ui.movies

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.minhle.flickkfinal.Injection
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.base.BaseFragment
import com.minhle.flickkfinal.databinding.FragmentMoviesBinding
import com.minhle.flickkfinal.model.Movie
import com.minhle.flickkfinal.ui.viewmodel.MoviesViewModel
import com.minhle.flickkfinal.utils.isConnected
import com.minhle.flickkfinal.utils.Resource
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_movies.*


class MoviesFragment : BaseFragment() {


    private val binding: FragmentMoviesBinding
        get() = (getViewBinding() as FragmentMoviesBinding)
    private val controller by lazy {
        findNavController()
    }
    private val moviesViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideMoviesViewModelFactory()
        )[MoviesViewModel::class.java]
    }
    private val moviesAdapter by lazy {
        MoviesAdapter(openMovieDetail)
    }
    private val nowPlayingAdapter by lazy {
        NowPlayingAdapter(openMovieDetail)
    }

    private val topRateAdapter by lazy {
        TopRateAdapter(openMovieDetail)
    }

    private val sliderImageAdapter by lazy {
        SliderAdapter(requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }


    override fun getLayoutId(): Int = R.layout.fragment_movies

    override fun initControls(view: View, savedInstanceState: Bundle?) {

        (activity as AppCompatActivity).setSupportActionBar(binding.mainToolBar)

        binding.rvMovies.layoutManager =
            GridLayoutManager(this.context, 2, RecyclerView.HORIZONTAL, false)
        binding.rvMovies.setHasFixedSize(true)
        binding.rvMovies.adapter = moviesAdapter

        binding.rvNowPlay.layoutManager =
            GridLayoutManager(this.context, 2, RecyclerView.HORIZONTAL, false)
        binding.rvNowPlay.setHasFixedSize(true)
        binding.rvNowPlay.adapter = nowPlayingAdapter

        binding.rvRateMovie.layoutManager =
            LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        binding.rvRateMovie.setHasFixedSize(true)
        binding.rvRateMovie.adapter = topRateAdapter

        //slider
        binding.imageSlider.apply {
            setSliderAdapter(sliderImageAdapter)
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH

            scrollTimeInSec = 3
            isAutoCycle = true
            startAutoCycle()
        }
        if (requireContext().isConnected) {
            refreshData()
            refreshDataNowPlay()
            refreshDataRateMovie()
            refreshSlider()
            requestPermission()
        }
        else {
            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
        }


    }


    override fun initEvents() {

        binding.swipe.setOnRefreshListener {
            if (requireContext().isConnected) {
                refreshData()
                refreshDataNowPlay()
                refreshDataRateMovie()
                refreshSlider()
                requestPermission()
            }
            else {
                Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
            }
            swipe.isRefreshing = false
        }

        sliderImageAdapter.setOnclickListener {
            val bundle = bundleOf(
                "MOVIES_DETAIL" to it
            )
            controller.navigate(R.id.action_nav_movies_to_nav_detail, bundle)
        }

        binding.txtNowPlayMore.setOnClickListener {
            val fragment = Fragment()
            val bundle = Bundle()
            bundle.putInt("key_check", 1)
            fragment.arguments = bundle
            controller.navigate(R.id.action_nav_movies_to_nowPlayingFragment, bundle)
        }
        binding.txtBestRateMore.setOnClickListener {
            val fragment = Fragment()
            val bundle = Bundle()
            bundle.putInt("key_check", 2)
            fragment.arguments = bundle
            controller.navigate(R.id.action_nav_movies_to_nowPlayingFragment, bundle)
        }
        binding.txtPopularMore.setOnClickListener {
            val fragment = Fragment()
            val bundle = Bundle()
            bundle.putInt("key_check", 3)
            fragment.arguments = bundle
            controller.navigate(R.id.action_nav_movies_to_nowPlayingFragment, bundle)
        }

    }

    private val openMovieDetail: (Movie) -> Unit = {
        val bundle = bundleOf(
            "MOVIES_DETAIL" to it
        )

        controller.navigate(R.id.action_nav_movies_to_nav_detail, bundle)
    }

    private fun refreshDataRateMovie() {
        moviesViewModel.getTopRated(1).observe(viewLifecycleOwner, {
            binding.nestMovie.isVisible = it is Resource.Success
            when (it) {
                is Resource.Success -> {
                    topRateAdapter.movie = it.data.movies
                    hideProgressBar()
                }
                is Resource.Error -> {
                    showProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()

                }
            }
        })
    }

    private fun refreshSlider() {
        moviesViewModel.getTopRated(1).observe(viewLifecycleOwner, {

            binding.nestMovie.isVisible = it is Resource.Success
            when (it) {
                is Resource.Success -> {
                    val item = it.data.movies.subList(0, 5)
                    sliderImageAdapter.renewItems(item as MutableList<Movie>)
                    hideProgressBar()
                }
                is Resource.Error -> {
                    showProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()

                }
            }
        })
    }

    private fun refreshDataNowPlay() {
        moviesViewModel.getNowMovies(1).observe(viewLifecycleOwner, {
            binding.nestMovie.isVisible = it is Resource.Success
            when (it) {
                is Resource.Success -> {
                    nowPlayingAdapter.movie = it.data.movies
                    hideProgressBar()
                }
                is Resource.Error -> {
                    showProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }


    private fun refreshData() {
        moviesViewModel.getPopularMovies(1).observe(viewLifecycleOwner, {
            binding.nestMovie.isVisible = it is Resource.Success
            when (it) {
                is Resource.Success -> {
                    moviesAdapter.movie = it.data.movies
                    hideProgressBar()
                }
                is Resource.Error -> {
                    it.message.let { message ->
                        Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show()
                    }
                    showProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_avatar) {
            controller.navigate(R.id.action_nav_movies_to_nav_user)
        } else if (item.itemId == R.id.action_search) {
            controller.navigate(R.id.action_nav_movies_to_nav_search)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestPermission() {
        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    context,
                    "Permission Denied\n$deniedPermissions",
                    Toast.LENGTH_SHORT
                ).show()
                requestPermission()
            }
        }
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.RECORD_AUDIO,
            )
            .check()


    }

    private fun hideProgressBar() {
        binding.progressCircular.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressCircular.visibility = View.VISIBLE
    }


}


