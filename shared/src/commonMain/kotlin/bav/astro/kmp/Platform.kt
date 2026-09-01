package bav.astro.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform