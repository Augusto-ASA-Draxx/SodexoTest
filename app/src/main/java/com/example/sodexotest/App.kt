package com.example.sodexotest

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.iteris.portallogistica.utils.di.Modules
import org.koin.android.ext.android.startKoin

open class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin(this, Modules.modules)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        private lateinit var context: Context

        operator fun get(context: Context?): App {
            return context?.applicationContext as App
        }

        fun getContext(): Context {
            return context
        }
        fun getInstance(): App = context as App
    }
}