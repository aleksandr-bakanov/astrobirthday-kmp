package bav.astro.kmp.shared.ui.add_person

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.repository.PersonRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

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
                    Person(name = name, birthday = convertBirthdayForDb(birthday))
                )
                onSuccess()
            } finally {
                isSubmitting = false
            }
        }
    }

    private fun convertBirthdayForDb(birthday: String): String {
        val parts = birthday.split("-")
        return "${parts[2]}-${parts[1]}-${parts[0]}"
    }

    private fun isValidDate(date: String): Boolean {
        val regexValid = DATE_REGEX.matches(date)
        return if (regexValid) {
            val parts = date.split("-")
            try {
                LocalDate.parse("${parts[2]}-${parts[1]}-${parts[0]}")
                true
            } catch (_ : IllegalArgumentException) {
                false
            }
        } else false
    }

    private companion object {
        private val DATE_REGEX = Regex("""\d{2}-\d{2}-\d{4}""")
    }
}
