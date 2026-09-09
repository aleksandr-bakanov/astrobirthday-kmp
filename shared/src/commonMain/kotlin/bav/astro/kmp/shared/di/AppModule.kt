package bav.astro.kmp.shared.di

import bav.astro.kmp.shared.database.AppDatabase
import bav.astro.kmp.shared.database.getRoomDatabase
import bav.astro.kmp.shared.repository.PersonRepository
import org.koin.dsl.module

val commonModule = module {
    single { getRoomDatabase(get()) }
    single { get<AppDatabase>().personDao() }
    single { PersonRepository(get()) }
}

val appModule = listOf(commonModule, platformModule)
