package com.tipiz.newsappuninet.di

import android.app.Application
import com.tipiz.core.di.CoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainKoin : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainKoin)
            modules(AppModules.modules)
            modules(CoreModule.modules)
        }
    }
}