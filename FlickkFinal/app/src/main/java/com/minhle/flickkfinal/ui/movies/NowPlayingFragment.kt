package com.minhle.flickkfinal.ui.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.fragment_movies.*
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



        val bundle = this.arguments
        if (bundle != null) {
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
        }


    }


    override fun initEvents() {
        binding.toolbarPlay.setNavigationOnClickListener {
            controller.popBackStack()
        }

        binding.swipeLayout.setOnRefreshListener {
            val bundle = this.arguments
            if (bundle != null) {
                when (bundle.getInt("key_check", 0)) {
                    1 -> {
                        refreshData()
                    }
                    2 -> {
                        refreshDataTopRate()
                    }
                    3 -> {
                        refreshDataPopular()
                    }
                }
            }
        }

        moreMoviesAdapter.setOnclickListener {
            val bundle = bundleOf(
                "MOVIES_DETAIL" to it
            )
            controller.navigate(R.id.action_nav_now_playing_to_nav_detail, bundle)
        }
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
       /* binding.rvNowPlaying.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY*/
        binding.rvNowPlaying.adapter = moreMoviesAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { moreMoviesAdapter.retry() },
            footer = MovieLoadStateAdapter { moreMoviesAdapter.retry() }
        )
    }
}