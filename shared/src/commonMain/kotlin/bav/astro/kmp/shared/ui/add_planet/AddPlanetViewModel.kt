package bav.astro.kmp.shared.ui.add_planet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.database.PlanetEntity
import bav.astro.kmp.shared.repository.PersonRepository
import bav.astro.kmp.shared.repository.PlanetRepository
import bav.astro.kmp.shared.util.convertBirthdayForDb
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class AddPlanetViewModel(
    private val repository: PlanetRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var period by mutableStateOf("") // dd-mm-yyyy

    var isSubmitting by mutableStateOf(false)
        private set

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
                repository.insertPlanet(
                    PlanetEntity(
                        name = name,
                        period = period.toDouble(),
                        isVisible = true,
                    )
                )
                onSuccess()
            } finally {
                isSubmitting = false
            }
        }
    }

    private fun isValidPeriod(period: String): Boolean {
        return try {
            period.toDouble()
            true
        } catch (_ : NumberFormatException) {
            false
        }
    }
}
