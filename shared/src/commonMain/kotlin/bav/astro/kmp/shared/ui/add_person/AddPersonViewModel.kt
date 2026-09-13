package bav.astro.kmp.shared.ui.add_person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.repository.PersonRepository
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
                repository.insertPerson(Person(name = name, birthday = birthday))
                onSuccess()
            } finally {
                isSubmitting = false
            }
        }
    }

    private fun isValidDate(date: String): Boolean {
        // Simple regex for dd-mm-yyyy
        return Regex("""\d{2}-\d{2}-\d{4}""").matches(date)
    }
}
