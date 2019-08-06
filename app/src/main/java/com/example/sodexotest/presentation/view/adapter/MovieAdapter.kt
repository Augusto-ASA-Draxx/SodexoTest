package com.example.sodexotest.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.sodexotest.R
import com.example.sodexotest.data.model.Movie
import com.example.sodexotest.databinding.MovieItemBinding
import com.example.sodexotest.presentation.view.listeners.CustomClickListener

class MovieAdapter (private var movieList: MutableList<Movie>,
                    private var listener: CustomClickListener): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false) as MovieItemBinding
        return ViewHolder(binding)
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
        holder.movieItemBinding.itemClickListener = listener
    }

    open inner class ViewHolder(var movieItemBinding: MovieItemBinding) : RecyclerView.ViewHolder(movieItemBinding.root) {

        fun bind(movie: Movie){
            movieItemBinding.setVariable(BR.movies, movie)
            movieItemBinding.executePendingBindings()
        }
    }


}