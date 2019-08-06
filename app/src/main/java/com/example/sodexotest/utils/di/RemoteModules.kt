package com.iteris.portallogistica.utils.di

import com.example.sodexotest.utils.di.Config
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteModules {

    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .addInterceptor{ chain ->
                val newRequest = chain.request().newBuilder()
                    .header("trakt-api-key", Config.API_KEY)
                    .header("trakt-api-version", Config.API_VERSION)
                    .build()
                chain.proceed(newRequest)
            }

        return builder.build()
    }

    inline fun <reified T> provideRetrofit(okHttpClient: OkHttpClient): T {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(T::class.java)
    }

}