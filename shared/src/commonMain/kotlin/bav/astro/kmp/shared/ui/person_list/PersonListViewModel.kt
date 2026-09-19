package bav.astro.kmp.shared.ui.person_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.repository.PersonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonListViewModel(
    private val repository: PersonRepository
) : ViewModel() {
    val uiState: StateFlow<List<Person>> = repository.getAllPeople()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun switchPersonVisibility(person: Person) {
        viewModelScope.launch {
            repository.updatePerson(
                person.copy(isVisible = !person.isVisible)
            )
        }
    }
}
