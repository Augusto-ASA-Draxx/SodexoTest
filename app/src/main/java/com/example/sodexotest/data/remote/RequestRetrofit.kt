package com.example.sodexotest.data.remote

import com.example.sodexotest.data.model.Movie
import com.example.sodexotest.data.model.MovieDetail
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestRetrofit {

    @GET("movies/popular")
    fun getMoviesList(): Single<Response<MutableList<Movie>>>

    @GET("movies/{id_movie}")
    fun getMovieDetail(@Path(value = "id_movie", encoded = true) idMovie: String,
                       @Query("extended", encoded = true) extended: String): Single<Response<MovieDetail>>

}