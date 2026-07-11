package com.faezolmp.momeapp.presentation.di

import com.faezolmp.momeapp.core.domain.usecase.BudgetInteractor
import com.faezolmp.momeapp.core.domain.usecase.BudgetUseCase
import com.faezolmp.momeapp.core.domain.usecase.CategoryInteractor
import com.faezolmp.momeapp.core.domain.usecase.CategoryUseCase
import com.faezolmp.momeapp.core.domain.usecase.TransactionInteractor
import com.faezolmp.momeapp.core.domain.usecase.TransactionUseCase
import com.faezolmp.momeapp.core.domain.usecase.UseCase
import com.faezolmp.momeapp.core.domain.usecase.UseCaseIteractor
import com.faezolmp.momeapp.core.notification.BudgetNotifier
import org.koin.android.ext.koin.androidContext
import com.faezolmp.momeapp.presentation.screen.AddManual.AddTransactionViewModel
import com.faezolmp.momeapp.presentation.screen.Confirm.ConfirmViewModel
import com.faezolmp.momeapp.presentation.screen.Dashboard.DashboardViewModel
import com.faezolmp.momeapp.presentation.screen.Detail.DetailViewModel
import com.faezolmp.momeapp.presentation.screen.Edit.EditTransactionViewModel
import com.faezolmp.momeapp.presentation.screen.History.HistoryViewModel
import com.faezolmp.momeapp.presentation.screen.Manage.BudgetViewModel
import com.faezolmp.momeapp.presentation.screen.Manage.ManageCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<UseCase> { UseCaseIteractor(get()) }
    single<TransactionUseCase> { TransactionInteractor(get()) }
    single<CategoryUseCase> { CategoryInteractor(get()) }
    single<BudgetUseCase> { BudgetInteractor(get()) }
    single { BudgetNotifier(androidContext(), get(), get()) }

    viewModel { DashboardViewModel(get(), get(), get()) }
    viewModel { HistoryViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { EditTransactionViewModel(get(), get(), get()) }
    viewModel { ConfirmViewModel(get(), get(), get()) }
    viewModel { AddTransactionViewModel(get(), get(), get()) }
    viewModel { ManageCategoryViewModel(get(), get()) }
    viewModel { BudgetViewModel(get()) }
}
