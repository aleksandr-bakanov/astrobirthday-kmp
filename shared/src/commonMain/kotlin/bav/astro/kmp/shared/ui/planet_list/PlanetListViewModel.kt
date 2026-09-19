package bav.astro.kmp.shared.ui.planet_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.database.PlanetEntity
import bav.astro.kmp.shared.repository.PersonRepository
import bav.astro.kmp.shared.repository.PlanetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlanetListViewModel(
    private val repository: PlanetRepository
) : ViewModel() {
    val uiState: StateFlow<List<PlanetEntity>> = repository.getAllPlanets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun switchPlanetVisibility(planetEntity: PlanetEntity) {
        viewModelScope.launch {
            repository.updatePlanet(
                planetEntity.copy(isVisible = !planetEntity.isVisible)
            )
        }
    }
}
