package bav.astro.kmp.shared.ui.planet_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.planets.Planet
import bav.astro.kmp.shared.repository.PlanetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlanetListViewModel(
    private val repository: PlanetRepository
) : ViewModel() {
    val uiState: StateFlow<List<Planet>> = repository.getAllPlanets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun switchPlanetVisibility(planet: Planet) {
        viewModelScope.launch {
            repository.updatePlanetVisibility(
                planet = planet,
                isVisible = !planet.isVisible,
            )
        }
    }
}
