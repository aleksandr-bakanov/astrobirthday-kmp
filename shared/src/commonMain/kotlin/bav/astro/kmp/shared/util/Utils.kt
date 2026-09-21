package bav.astro.kmp.shared.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn
import kotlin.time.Clock

fun convertBirthdayForDb(birthday: String): String {
    val parts = birthday.split("-")
    return "${parts[2]}-${parts[1]}-${parts[0]}"
}

private val DATE_REGEX = Regex("""\d{2}-\d{2}-\d{4}""")

fun isValidDate(date: String): Boolean {
    val regexValid = DATE_REGEX.matches(date)
    return if (regexValid) {
        val parts = date.split("-")
        try {
            val localDate = LocalDate.parse("${parts[2]}-${parts[1]}-${parts[0]}")
            val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

            // Must be in the past or today
            localDate.daysUntil(today) >= 0

        } catch (_ : IllegalArgumentException) {
            false
        }
    } else false
}

fun isValidPeriod(period: String): Boolean {
    return try {
        val dPeriod = period.toDouble()
        // Must be positive
        dPeriod > 0.0
    } catch (_ : NumberFormatException) {
        false
    }
}