package bav.astro.kmp.shared.ui.birthdays

import bav.astro.kmp.shared.planets.PlanetType
import kotlinx.datetime.LocalDate

/**
 * @param name Person's name
 * @param planetType Planet which is related to birthday
 * @param currentAge Current age in years (on mentioned planet)
 * @param birthday Date of some birthday (maybe nearest, maybe next one after nearest)
 */
data class PersonBirthdayData(
    val name: String,
    val planetType: PlanetType,
    //val currentAge: Double,
    val birthday: LocalDate,
)
