package com.example.sodexotest.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import com.example.sodexotest.R
import com.example.sodexotest.data.model.Movie
import com.example.sodexotest.data.model.MovieDetail
import com.example.sodexotest.databinding.ActivityMainBinding
import com.example.sodexotest.presentation.view.adapter.MovieAdapter
import com.example.sodexotest.presentation.view.listeners.CustomClickListener
import com.example.sodexotest.presentation.viewModel.MainViewModel
import com.example.sodexotest.utils.DialogFactory
import com.example.sodexotest.utils.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private var loading: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        configureView()
        viewModel.getState().observe(this, Observer { handleState(it) })
    }

    private fun configureView() {
        binding.viewModel = viewModel
        binding.viewModel?.getMoviesList()

    }

    private fun handleState(state: MainViewModel.State) {
        when (state) {
            is MainViewModel.State.Error -> {
                dismissLoading()
            }
            MainViewModel.State.Loading -> {
                showLoading()
            }

            is MainViewModel.State.RetrievedMovie -> {
                dismissLoading()
                configAdapter(state.modelMovie)
            }

            is MainViewModel.State.RetrievedMovieDetail -> {
                dismissLoading()
                showMovieDetail(state.modelMovieDetail)
            }
        }
    }

    private fun configAdapter(model: MutableList<Movie>) {
        val movieAdapter = MovieAdapter(model, movieClickListener())
        binding.adapter = movieAdapter
    }

    private fun movieClickListener(): CustomClickListener {
        return object : CustomClickListener {
            override fun movieClicked(movie: Movie) {
                binding.viewModel?.getMovieDetail(movie.ids.slug)
            }
        }
    }

    fun showMovieDetail(movieDetail: MovieDetail) {
        var movieDetailDialogFragment = MovieDetailDialogFragment()
        movieDetailDialogFragment.setMovieDetail(movieDetail)
        movieDetailDialogFragment.show(supportFragmentManager, "MovieDetailDialogFragment")
    }

    fun showLoading() {
        if (loading == null) {
            loading = DialogFactory.getLoading()
            loading?.show(supportFragmentManager, "")
        } else
            if (loading?.isVisible == false)
                loading?.show(supportFragmentManager, "")
    }

    fun dismissLoading() {
        loading?.dismissAllowingStateLoss()
    }

}
