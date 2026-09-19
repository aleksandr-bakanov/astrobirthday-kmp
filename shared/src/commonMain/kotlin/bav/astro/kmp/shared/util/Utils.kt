package bav.astro.kmp.shared.util

fun convertBirthdayForDb(birthday: String): String {
    val parts = birthday.split("-")
    return "${parts[2]}-${parts[1]}-${parts[0]}"
}