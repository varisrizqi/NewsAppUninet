package com.tipiz.newsappuninet.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import com.tipiz.newsappuninet.ui.home.HomeViewModel
import org.koin.dsl.module

object AppModules {

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)

    }

    val modules: List<Module> = listOf(
        viewModelModule
    )
}