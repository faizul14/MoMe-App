package com.faezolmp.projek_android_template.core.di

import com.faezolmp.projek_android_template.core.data.ImplRepository
import com.faezolmp.projek_android_template.core.domain.repository.Repository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
//    single <Repository> { ImplRepository() }
//    or
    singleOf(::ImplRepository) { bind<Repository>() }
}