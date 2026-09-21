package bav.astro.kmp.shared.ui.edit_planet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.database.PlanetEntity
import bav.astro.kmp.shared.repository.PlanetRepository
import bav.astro.kmp.shared.util.isValidPeriod
import kotlinx.coroutines.launch

class EditPlanetViewModel(
    private val planetId: Int,
    private val repository: PlanetRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var period by mutableStateOf("") // in Earth days

    var isSubmitting by mutableStateOf(false)
        private set

    var isDeleting by mutableStateOf(false)
        private set

    var planet: PlanetEntity? = null

    init {
        viewModelScope.launch {
            repository.getPlanetById(planetId)?.let { planet ->
                name = planet.name
                period = planet.period.toString()
                this@EditPlanetViewModel.planet = planet
            }
        }
    }

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onPeriodChange(newPeriod: String) {
        period = newPeriod
    }

    fun submit(onSuccess: () -> Unit) {
        if (name.isBlank() || !isValidPeriod(period)) return

        viewModelScope.launch {
            isSubmitting = true
            try {
                repository.updatePlanet(
                    PlanetEntity(
                        id = planetId,
                        name = name,
                        period = period.toDouble(),
                        isVisible = planet?.isVisible ?: true
                    )
                )
                onSuccess()
            } finally {
                isSubmitting = false
            }
        }
    }

    fun delete(onSuccess: () -> Unit) {
        planet?.let { p ->
            viewModelScope.launch {
                isDeleting = true
                try {
                    repository.deletePlanet(p)
                    onSuccess()
                } finally {
                    isDeleting = false
                }
            }
        }
    }
}
