package com.example.sodexotest.data.remote


import com.example.sodexotest.data.model.Movie
import com.example.sodexotest.data.model.MovieDetail
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

interface ServiceProvider {
    fun getMoviesList(): Single<MutableList<Movie>>
    fun getMovieDetail(idMovie: String): Single<MovieDetail>
}

class ServiceClient constructor(private val service: RequestRetrofit) : ServiceProvider {

    override fun getMoviesList(): Single<MutableList<Movie>> {
        return Single.create { single ->
            service.getMoviesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {

                    single.onSuccess(it.body())
                }, onError = {
                    single.onError(it)
                })
        }
    }

    override fun getMovieDetail(idMovie: String): Single<MovieDetail> {
        return Single.create { single ->
            service.getMovieDetail(idMovie, "full")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    single.onSuccess(it.body())
                }, onError = {
                    single.onError(it)
                })
        }
    }
}
