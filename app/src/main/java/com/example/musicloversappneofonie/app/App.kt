package com.example.musicloversappneofonie.app

import android.app.Application
import com.example.musicloversappneofonie.dependency_injection.appModule
import com.example.musicloversappneofonie.dependency_injection.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(networkModule, appModule))
        }
    }
}