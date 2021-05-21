package com.minhle.flickkfinal.ui.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minhle.flickkfinal.Injection
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.base.BaseFragment
import com.minhle.flickkfinal.databinding.FragmentPlayingBinding
import com.minhle.flickkfinal.ui.viewmodel.MoviesViewModel
import com.minhle.flickkfinal.utils.isConnected
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NowPlayingFragment : BaseFragment() {
    private val moreMoviesAdapter by lazy {
        MoreMoviesAdapter()
    }


    private var job: Job? = null


    private val binding: FragmentPlayingBinding
        get() = (getViewBinding() as FragmentPlayingBinding)

    private val controller by lazy {
        findNavController()
    }
    private val moviesViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideMoviesViewModelFactory()
        )[MoviesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutId(): Int = R.layout.fragment_playing
    override fun initControls(view: View, savedInstanceState: Bundle?) {

        setData()

        binding.toolbarPlay.title = "Polular"
        val bundle = this.arguments
        if (bundle != null) {
                when (bundle.getInt("key_check", 0)) {
                    1 -> {
                        binding.toolbarPlay.title = "Now playing"
                    }
                    2 -> {
                        binding.toolbarPlay.title = "Top Rating"
                    }
                    3 -> {
                        binding.toolbarPlay.title = "Most Popular"
                    }
                }
            }

    }


    override fun initEvents() {
        binding.toolbarPlay.setNavigationOnClickListener {
            controller.popBackStack()
        }

        binding.swipeLayout.setOnRefreshListener {
            setData()
            binding.swipeLayout.isRefreshing = false
        }

        moreMoviesAdapter.setOnclickListener {
            val bundle = bundleOf(
                "MOVIES_DETAIL" to it
            )
            controller.navigate(R.id.action_nav_now_playing_to_nav_detail, bundle)
        }

        binding.retryButton.setOnClickListener { setData() }
    }


    private fun refreshData() {
        job?.cancel()
        job = lifecycleScope.launch {
            moviesViewModel.getMoviesFromApi().collectLatest {
                binding.swipeLayout.isRefreshing = false
                moreMoviesAdapter.submitData(it)
            }

        }
    }

    private fun refreshDataTopRate() {
        job?.cancel()
        job = lifecycleScope.launch {
            moviesViewModel.getMoviesTopRate().collectLatest {
                binding.swipeLayout.isRefreshing = false
                moreMoviesAdapter.submitData(it)
            }

        }
    }

    private fun refreshDataPopular() {
        job?.cancel()
        job = lifecycleScope.launch {
            moviesViewModel.getMoviesPopular().collectLatest {
                binding.swipeLayout.isRefreshing = false

                moreMoviesAdapter.submitData(it)
            }

        }
    }

    private fun setUpRecyclerView() {
        binding.rvNowPlaying.setHasFixedSize(true)
        binding.rvNowPlaying.layoutManager =
            GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
        binding.rvNowPlaying.adapter = moreMoviesAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { moreMoviesAdapter.retry() },
            footer = MovieLoadStateAdapter { moreMoviesAdapter.retry() }
        )
    }

    private fun hideNotification() {
        binding.emptyList.visibility = View.INVISIBLE
        binding.retryButton.visibility = View.INVISIBLE
        binding.rvNowPlaying.visibility = View.VISIBLE
    }

    private fun showNotification() {
        binding.rvNowPlaying.visibility = View.INVISIBLE
        binding.emptyList.visibility = View.VISIBLE
        binding.retryButton.visibility = View.VISIBLE
    }
    private fun setData() {
        val bundle = this.arguments
        if (bundle != null) {
            if (requireContext().isConnected) {
                hideNotification()
                when (bundle.getInt("key_check", 0)) {
                    1 -> {
                        setUpRecyclerView()
                        refreshData()
                    }
                    2 -> {
                        setUpRecyclerView()
                        refreshDataTopRate()
                    }
                    3 -> {
                        setUpRecyclerView()
                        refreshDataPopular()
                    }
                }
            }else{
                Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                showNotification()
            }
        }
    }

}