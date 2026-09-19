package bav.astro.kmp.shared.ui.birthdays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.planets.PlanetUtils
import bav.astro.kmp.shared.repository.PersonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class BirthdaysViewModel(
    repository: PersonRepository,
) : ViewModel() {
    val uiState: StateFlow<List<PersonBirthdayData>> = repository.getVisiblePeople()
        .map { people ->
            PlanetUtils.getBirthdays(
                persons = people,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
