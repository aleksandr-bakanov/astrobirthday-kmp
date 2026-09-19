package bav.astro.kmp.shared.di

import bav.astro.kmp.shared.database.AppDatabase
import bav.astro.kmp.shared.database.getRoomDatabase
import bav.astro.kmp.shared.repository.PersonRepository
import bav.astro.kmp.shared.ui.add_person.AddPersonViewModel
import bav.astro.kmp.shared.ui.birthdays.BirthdaysViewModel
import bav.astro.kmp.shared.ui.edit_person.EditPersonViewModel
import bav.astro.kmp.shared.ui.person_list.PersonListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val commonModule = module {
    single { getRoomDatabase(get()) }
    single { get<AppDatabase>().personDao() }
    single { PersonRepository(get()) }
    viewModelOf(::PersonListViewModel)
    viewModelOf(::AddPersonViewModel)
    viewModelOf(::BirthdaysViewModel)
    viewModel { params ->
        EditPersonViewModel(
            personId = params.get(),
            repository = get(),
        )
    }
}

val appModule = listOf(commonModule, platformModule)
