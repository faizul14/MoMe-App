package com.faezolmp.momeapp.core.di

import com.faezolmp.momeapp.core.data.ImplRepository
import com.faezolmp.momeapp.core.data.repository.BudgetRepositoryImpl
import com.faezolmp.momeapp.core.data.repository.CategoryRepositoryImpl
import com.faezolmp.momeapp.core.data.repository.ProfileRepositoryImpl
import com.faezolmp.momeapp.core.data.repository.TransactionRepositoryImpl
import com.faezolmp.momeapp.core.domain.repository.BudgetRepository
import com.faezolmp.momeapp.core.domain.repository.CategoryRepository
import com.faezolmp.momeapp.core.domain.repository.ProfileRepository
import com.faezolmp.momeapp.core.domain.repository.Repository
import com.faezolmp.momeapp.core.domain.repository.TransactionRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ImplRepository) { bind<Repository>() }
    singleOf(::TransactionRepositoryImpl) { bind<TransactionRepository>() }
    singleOf(::CategoryRepositoryImpl) { bind<CategoryRepository>() }
    singleOf(::BudgetRepositoryImpl) { bind<BudgetRepository>() }
    singleOf(::ProfileRepositoryImpl) { bind<ProfileRepository>() }
}
