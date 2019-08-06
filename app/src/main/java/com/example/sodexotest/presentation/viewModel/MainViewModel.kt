package com.example.sodexotest.presentation.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sodexotest.data.model.Movie
import com.example.sodexotest.data.model.MovieDetail
import com.example.sodexotest.data.remote.ServiceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val serviceProvider: ServiceProvider) : ViewModel() {

    sealed class State {
        data class Error(val message: String) : State()
        object Loading : State()
        data class RetrievedMovie(val modelMovie: MutableList<Movie>) : State()
        data class RetrievedMovieDetail(val modelMovieDetail: MovieDetail) : State()
    }

    var modelMovie: ObservableField<MutableList<Movie>> = ObservableField()
    var modelMovieDetail: ObservableField<MovieDetail> = ObservableField()
    private var state: MutableLiveData<State> = MutableLiveData()

    fun getState(): LiveData<State> = state

    fun getMoviesList() {
        serviceProvider.getMoviesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                state.postValue(State.Loading)
            }
            .subscribeBy(onSuccess = {
                state.postValue(State.RetrievedMovie(it))
                modelMovie.set(it)
            }, onError = {
                state.postValue(State.Error(message = it.localizedMessage))
            })
    }

    fun getMovieDetail(idMovie: String) {
        serviceProvider.getMovieDetail(idMovie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                state.postValue(State.Loading)
            }
            .subscribeBy(onSuccess = {
                state.postValue(State.RetrievedMovieDetail(it))
                modelMovieDetail.set(it)
            }, onError = {
                state.postValue(State.Error(message = it.localizedMessage))
            })
    }

}