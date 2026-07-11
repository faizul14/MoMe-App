package com.faezolmp.momeapp.core.di

import com.faezolmp.momeapp.core.data.ImplRepository
import com.faezolmp.momeapp.core.domain.repository.Repository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
//    single <Repository> { ImplRepository() }
//    or
    singleOf(::ImplRepository) { bind<Repository>() }
}