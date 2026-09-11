package bav.astro.kmp.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.repository.PersonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PersonListViewModel(
    repository: PersonRepository
) : ViewModel() {
    val uiState: StateFlow<List<Person>> = repository.getAllPeople()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
