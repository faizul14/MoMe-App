package com.faezolmp.projek_android_template.presentation.di

import androidx.lifecycle.ViewModel
import com.faezolmp.projek_android_template.core.domain.usecase.UseCase
import com.faezolmp.projek_android_template.core.domain.usecase.UseCaseIteractor
import com.faezolmp.projek_android_template.presentation.screen.Home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<UseCase> { UseCaseIteractor(get()) }

    viewModel<ViewModel> { HomeViewModel(get()) }
    viewModelOf(::HomeViewModel) { bind<ViewModel>() }
}
