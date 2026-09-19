package bav.astro.kmp.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavKey {
    @Serializable
    data object Birthdays : NavKey

    @Serializable
    data object PersonList : NavKey

    @Serializable
    data object AddPerson : NavKey

    @Serializable
    data class EditPerson(
        val personId: Int,
    ) : NavKey

    @Serializable
    data object PlanetList : NavKey
}
