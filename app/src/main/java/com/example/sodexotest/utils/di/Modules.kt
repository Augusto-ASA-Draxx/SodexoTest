package com.iteris.portallogistica.utils.di

import com.example.sodexotest.data.remote.RequestRetrofit
import com.example.sodexotest.data.remote.ServiceClient
import com.example.sodexotest.data.remote.ServiceProvider
import com.example.sodexotest.presentation.viewModel.MainViewModel
import com.iteris.demomvvm.utils.rx.ApplicationSchedulerProvider
import com.iteris.demomvvm.utils.rx.SchedulerProvider
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object Modules {

    private val rxModule = module {
        single { ApplicationSchedulerProvider() as SchedulerProvider }
    }

    private val remoteModule = module {
        single { RemoteModules.provideOkHttpClient() }
        single { RemoteModules.provideRetrofit<RequestRetrofit>(get()) }
    }

    private val clientModule = module {
        single<ServiceProvider> { ServiceClient(get()) }
    }

    private val viewModelModule = module {
        viewModel { MainViewModel(get()) }

    }

    val modules = listOf(rxModule, remoteModule, clientModule, viewModelModule)
}