package bav.astro.kmp.shared.ui.edit_person

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

class EditPersonViewModel(
    private val personId: Int,
    private val repository: PersonRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var birthday by mutableStateOf("") // dd-mm-yyyy

    var isSubmitting by mutableStateOf(false)
        private set

    var isDeleting by mutableStateOf(false)
        private set

    var person: Person? = null

    init {
        viewModelScope.launch {
            repository.getPersonById(personId)?.let { person ->
                name = person.name
                birthday = convertBirthdayForDb(person.birthday)
                this@EditPersonViewModel.person = person
            }
        }
    }

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
                repository.updatePerson(
                    Person(
                        id = personId,
                        name = name,
                        birthday = convertBirthdayForDb(birthday),
                        isVisible = person?.isVisible ?: true
                    )
                )
                onSuccess()
            } finally {
                isSubmitting = false
            }
        }
    }

    fun delete(onSuccess: () -> Unit) {
        person?.let { p ->
            viewModelScope.launch {
                isDeleting = true
                try {
                    repository.deletePerson(p)
                    onSuccess()
                } finally {
                    isDeleting = false
                }
            }
        }
    }
}
