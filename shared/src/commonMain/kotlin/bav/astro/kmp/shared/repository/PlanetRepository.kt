package bav.astro.kmp.shared.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import bav.astro.kmp.shared.database.PlanetEntity
import bav.astro.kmp.shared.database.PlanetDao
import bav.astro.kmp.shared.planets.Planet
import bav.astro.kmp.shared.planets.PlanetType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class PlanetRepository(
    private val planetDao: PlanetDao,
    private val dataStore: DataStore<Preferences>,
) {
    fun getAllPlanets(): Flow<List<Planet>> = planetDao
        .getAllPlanets()
        .combine(dataStore.data) { planets, store ->
            val planetsFromDb = planets.map { 
                Planet(
                    id = it.id,
                    type = PlanetType.CUSTOM,
                    name = it.name,
                    period = it.period,
                    isVisible = it.isVisible,
                )
            }
            val homePlanets = solarPlanets.map {
                val visibilityKey = it.type.visibilityKey()
                Planet(
                    id = it.id,
                    type = it.type,
                    name = it.name,
                    period = it.period,
                    isVisible = visibilityKey?.let { key -> store[key] } ?: true
                )
            }

            planetsFromDb + homePlanets
    }

    fun getVisiblePlanets(): Flow<List<Planet>> = getAllPlanets()
        .map { it.filter { planet -> planet.isVisible } }

    suspend fun insertPlanet(planet: PlanetEntity) {
        planetDao.insert(planet)
    }

    suspend fun updatePlanet(planet: PlanetEntity) {
        planetDao.update(planet)
    }

    suspend fun updatePlanetVisibility(planet: Planet, isVisible: Boolean) {
        if (planet.type == PlanetType.CUSTOM) {
            planetDao.update(
                PlanetEntity(
                    id = planet.id,
                    name = planet.name,
                    period = planet.period,
                    isVisible = isVisible,
                )
            )
        } else {
            planet.type.visibilityKey()?.let { key ->
                dataStore.edit { store ->
                    store[key] = isVisible
                }
            }
        }
    }

    suspend fun deletePlanet(planet: PlanetEntity) {
        planetDao.delete(planet)
    }

    suspend fun getPlanetById(id: Int): PlanetEntity? = planetDao.getPlanetById(id)
    
    companion object {
        val solarPlanets = listOf(
            Planet(-1, PlanetType.MERCURY, "MERCURY", 87.969, true),
            Planet(-1, PlanetType.VENUS, "VENUS", 224.701, true),
            Planet(-1, PlanetType.EARTH, "EARTH", 365.25, true),
            Planet(-1, PlanetType.MARS, "MARS", 779.94, true),
            Planet(-1, PlanetType.JUPITER, "JUPITER", 4332.589, true),
            Planet(-1, PlanetType.SATURN, "SATURN", 10759.22, true),
            Planet(-1, PlanetType.URANUS, "URANUS", 30685.4, true),
            Planet(-1, PlanetType.NEPTUNE, "NEPTUNE", 60190.03, true),
            Planet(-1, PlanetType.PLUTO, "PLUTO", 90553.02, true),
        )

        val IS_VISIBLE_MERCURY = booleanPreferencesKey("IS_VISIBLE_MERCURY")
        val IS_VISIBLE_VENUS = booleanPreferencesKey("IS_VISIBLE_VENUS")
        val IS_VISIBLE_EARTH = booleanPreferencesKey("IS_VISIBLE_EARTH")
        val IS_VISIBLE_MARS = booleanPreferencesKey("IS_VISIBLE_MARS")
        val IS_VISIBLE_JUPITER = booleanPreferencesKey("IS_VISIBLE_JUPITER")
        val IS_VISIBLE_SATURN = booleanPreferencesKey("IS_VISIBLE_SATURN")
        val IS_VISIBLE_URANUS = booleanPreferencesKey("IS_VISIBLE_URANUS")
        val IS_VISIBLE_NEPTUNE = booleanPreferencesKey("IS_VISIBLE_NEPTUNE")
        val IS_VISIBLE_PLUTO = booleanPreferencesKey("IS_VISIBLE_PLUTO")
    }

    private fun PlanetType.visibilityKey(): Preferences.Key<Boolean>? = when(this) {
        PlanetType.MERCURY -> IS_VISIBLE_MERCURY
        PlanetType.VENUS -> IS_VISIBLE_VENUS
        PlanetType.EARTH -> IS_VISIBLE_EARTH
        PlanetType.MARS -> IS_VISIBLE_MARS
        PlanetType.JUPITER -> IS_VISIBLE_JUPITER
        PlanetType.SATURN -> IS_VISIBLE_SATURN
        PlanetType.URANUS -> IS_VISIBLE_URANUS
        PlanetType.NEPTUNE -> IS_VISIBLE_NEPTUNE
        PlanetType.PLUTO -> IS_VISIBLE_PLUTO
        else -> null
    }
}
