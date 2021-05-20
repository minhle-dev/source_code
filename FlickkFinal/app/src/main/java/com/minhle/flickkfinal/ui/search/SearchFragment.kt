package com.minhle.flickkfinal.ui.search

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minhle.flickkfinal.Injection
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.base.BaseFragment
import com.minhle.flickkfinal.databinding.FragmentSearchBinding
import com.minhle.flickkfinal.model.Movie
import com.minhle.flickkfinal.ui.movies.MoviesAdapter
import com.minhle.flickkfinal.ui.viewmodel.MoviesViewModel
import com.minhle.flickkfinal.utils.Resource
import com.minhle.flickkfinal.utils.isConnected
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class SearchFragment : BaseFragment() {

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

    private val binding: FragmentSearchBinding
        get() = (getViewBinding() as FragmentSearchBinding)


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutId(): Int = R.layout.fragment_search
    override fun initControls(view: View, savedInstanceState: Bundle?) {
        binding.rvSearch.layoutManager =
            GridLayoutManager(this.context, 2, RecyclerView.VERTICAL, false)
        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.adapter = moviesAdapter


        setDataSearch()

    }




    override fun initEvents() {
        binding.toolbarSearch.setNavigationOnClickListener {
            controller.popBackStack()
        }

        binding.ivVoice.setOnClickListener {
            getSpeechInput()
        }

        binding.swipe.setOnRefreshListener {
            setDataSearch()
            swipe.isRefreshing = false
        }

        binding.retryButton.setOnClickListener {
            setDataSearch()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode, data
        )
        when (requestCode) {
            10 -> if (resultCode == RESULT_OK &&
                data != null
            ) {
                val result =
                    data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS
                    )
                (binding.edtSearch as TextView).text = result!![0]
            }
        }
    }

    private fun getSpeechInput() {
        val intent = Intent(
            RecognizerIntent
                .ACTION_RECOGNIZE_SPEECH
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )

        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivityForResult(intent, 10)
        } else {
            Toast.makeText(
                context,
                "Your Device Doesn't Support Speech Input",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun searchMovie() {

        var job: Job? = null
        binding.edtSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(400)
                editable?.let {
                    if (editable.toString().trim().isNotEmpty()) {
                        moviesViewModel.getSearchMovie(editable.toString().trim(), 1)
                            .observe(viewLifecycleOwner, {
                                when (it) {
                                    is Resource.Success -> {
                                        if (it.data.movies.isEmpty()) {
                                            binding.txtNotiSearch.isVisible = true
                                            binding.swipe.isRefreshing = false
                                            binding.rvSearch.isVisible = false
                                        } else {
                                            moviesAdapter.movie = it.data.movies
                                            binding.txtNotiSearch.isVisible = false
                                            binding.rvSearch.isVisible = true
                                            binding.swipe.isRefreshing = false
                                        }
                                    }
                                    is Resource.Error -> {
                                        binding.swipe.isRefreshing = false
                                        binding.txtNotiSearch.isVisible = false

                                        it.message.let { message ->
                                            Toast.makeText(
                                                context,
                                                "" + message,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    }
                                    is Resource.Loading -> {
                                        binding.swipe.isRefreshing = true

                                    }
                                }
                            })

                    }
                }
            }
        }
    }

    private val openMovieDetail: (Movie) -> Unit = {
        val bundle = bundleOf(
            "MOVIES_DETAIL" to it
        )

        controller.navigate(R.id.action_nav_search_to_nav_detail, bundle)
    }

    private fun hideNotification() {
        binding.emptyList.visibility = View.INVISIBLE
        binding.retryButton.visibility = View.INVISIBLE
        binding.rvSearch.visibility = View.VISIBLE
    }

    private fun showNotification() {
        binding.rvSearch.visibility = View.INVISIBLE
        binding.emptyList.visibility = View.VISIBLE
        binding.retryButton.visibility = View.VISIBLE
    }

    private fun setDataSearch() {
        if (requireContext().isConnected) {
            hideNotification()
            searchMovie()
        } else {
            showNotification()
            Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
    }

}