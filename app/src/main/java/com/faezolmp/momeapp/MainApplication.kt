package com.faezolmp.momeapp

import android.app.Application
import com.faezolmp.momeapp.core.di.repositoryModule
import com.faezolmp.momeapp.presentation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication(): Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    repositoryModule,
                    appModule
                )
            )
        }
    }
}