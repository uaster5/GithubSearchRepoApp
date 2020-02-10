package com.example.githubsearchrepoapp.common.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import kotlin.coroutines.coroutineContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Setup Koin
        startKoin {
            androidContext(applicationContext)
            androidLogger()
            modules(appModule)
        }
    }
}