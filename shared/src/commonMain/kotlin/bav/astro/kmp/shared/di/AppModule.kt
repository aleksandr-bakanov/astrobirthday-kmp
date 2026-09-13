package bav.astro.kmp.shared.di

import bav.astro.kmp.shared.database.AppDatabase
import bav.astro.kmp.shared.database.getRoomDatabase
import bav.astro.kmp.shared.repository.PersonRepository
import bav.astro.kmp.shared.ui.add_person.AddPersonViewModel
import bav.astro.kmp.shared.ui.person_list.PersonListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val commonModule = module {
    single { getRoomDatabase(get()) }
    single { get<AppDatabase>().personDao() }
    single { PersonRepository(get()) }
    viewModelOf(::PersonListViewModel)
    viewModelOf(::AddPersonViewModel)
}

val appModule = listOf(commonModule, platformModule)
