package bav.astro.kmp.shared.ui.birthdays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.planets.PlanetUtils
import bav.astro.kmp.shared.repository.PersonRepository
import bav.astro.kmp.shared.repository.PlanetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BirthdaysViewModel(
    personRepository: PersonRepository,
    planetRepository: PlanetRepository,
) : ViewModel() {
    val uiState: StateFlow<List<PersonBirthdayData>> = personRepository.getVisiblePeople()
        .combine(planetRepository.getVisiblePlanets()) { people, planets ->
            PlanetUtils.getBirthdays(
                persons = people,
                planets = planets,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
