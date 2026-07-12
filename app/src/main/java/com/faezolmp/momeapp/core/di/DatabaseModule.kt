package com.faezolmp.momeapp.core.di

import androidx.room.Room
import com.faezolmp.momeapp.core.data.local.MomeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MomeDatabase::class.java,
            MomeDatabase.DATABASE_NAME
        )
            .addCallback(MomeDatabase.seedCallback)
            .addMigrations(MomeDatabase.MIGRATION_1_2)
            .build()
    }
    single { get<MomeDatabase>().transactionDao() }
    single { get<MomeDatabase>().categoryDao() }
    single { get<MomeDatabase>().budgetDao() }
    single { get<MomeDatabase>().profileDao() }
}
