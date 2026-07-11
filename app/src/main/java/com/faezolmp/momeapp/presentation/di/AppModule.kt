package com.faezolmp.momeapp.presentation.di

import androidx.lifecycle.ViewModel
import com.faezolmp.momeapp.core.domain.usecase.UseCase
import com.faezolmp.momeapp.core.domain.usecase.UseCaseIteractor
import com.faezolmp.momeapp.presentation.screen.Home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<UseCase> { UseCaseIteractor(get()) }

    viewModel<ViewModel> { HomeViewModel(get()) }
    viewModelOf(::HomeViewModel) { bind<ViewModel>() }
}
