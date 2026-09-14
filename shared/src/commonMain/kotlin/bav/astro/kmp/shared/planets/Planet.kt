package bav.astro.kmp.shared.planets

/**
 * @param period Period in Earth days
 */
data class Planet(
    val type: PlanetType,
    val period: Double,
)
