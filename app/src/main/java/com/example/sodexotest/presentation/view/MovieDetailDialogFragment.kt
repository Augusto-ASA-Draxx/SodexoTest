package com.example.sodexotest.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.DialogFragment
import com.example.sodexotest.R
import com.example.sodexotest.data.model.MovieDetail
import com.example.sodexotest.databinding.MovieDetailDialogFragmentBinding

class MovieDetailDialogFragment: DialogFragment() {

    private var binding: MovieDetailDialogFragmentBinding? = null
    private var movieDetail: MovieDetail? = null

    fun setMovieDetail(movieDetail: MovieDetail){
        this.movieDetail = movieDetail
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding = DataBindingUtil.inflate(LayoutInflater.from(container?.context), R.layout.movie_detail_dialog_fragment, container, false) as MovieDetailDialogFragmentBinding
        val view = inflater.inflate(R.layout.movie_detail_dialog_fragment, container, false)
        binding = DataBindingUtil.bind(view)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding?.setVariable(BR.movieDetail, movieDetail)
        binding?.executePendingBindings()
    }


}