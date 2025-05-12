package com.tipiz.core.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tipiz.core.data.datasource.NewsPagingSource
import com.tipiz.core.data.network.retrofit.ApiService
import com.tipiz.core.data.network.retrofit.NetworkClient
import com.tipiz.core.data.network.retrofit.RequestInterceptor
import com.tipiz.core.domain.repository.NewsRepository
import com.tipiz.core.domain.repository.NewsRepositoryImpl
import com.tipiz.core.utils.DataMapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object CoreModule {

    private val repositoryModule = module {
        single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
    }

    private val networkModules = module {
        single { RequestInterceptor() }
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { NetworkClient(get(), get()) }
        single<ApiService> { get<NetworkClient>().create() }

    }

    private val dataSourceModule = module {
        single { NewsPagingSource(get(), get(), get(), get(), get()) }

    }

    private val dataCore = module {
        single { DataMapper() }

    }

    val modules: List<Module> = listOf(
        repositoryModule,
        networkModules,
        dataSourceModule,
        dataCore
    )
}