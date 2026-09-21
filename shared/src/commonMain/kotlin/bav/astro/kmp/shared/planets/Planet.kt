package bav.astro.kmp.shared.planets

/**
 * @param period Period in Earth days
 */
data class Planet(
    val id: Int,
    val type: PlanetType,
    val name: String,
    val period: Double,
    val isVisible: Boolean,
)
