package bav.astro.kmp.shared.ui.add_person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.repository.PersonRepository
import bav.astro.kmp.shared.util.convertBirthdayForDb
import bav.astro.kmp.shared.util.isValidDate
import kotlinx.coroutines.launch

class AddPersonViewModel(
    private val repository: PersonRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var birthday by mutableStateOf("") // dd-mm-yyyy

    var isSubmitting by mutableStateOf(false)
        private set

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onBirthdayChange(newBirthday: String) {
        birthday = newBirthday
    }

    fun submit(onSuccess: () -> Unit) {
        if (name.isBlank() || !isValidDate(birthday)) return

        viewModelScope.launch {
            isSubmitting = true
            try {
                repository.insertPerson(
                    Person(
                        name = name,
                        birthday = convertBirthdayForDb(birthday),
                        isVisible = true,
                    )
                )
                onSuccess()
            } finally {
                isSubmitting = false
            }
        }
    }
}
