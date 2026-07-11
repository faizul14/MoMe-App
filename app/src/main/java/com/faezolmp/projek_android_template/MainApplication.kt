package com.faezolmp.projek_android_template

import android.app.Application
import com.faezolmp.projek_android_template.core.di.repositoryModule
import com.faezolmp.projek_android_template.presentation.di.appModule
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