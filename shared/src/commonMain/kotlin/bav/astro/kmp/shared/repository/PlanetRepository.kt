package bav.astro.kmp.shared.repository

import bav.astro.kmp.shared.database.PlanetEntity
import bav.astro.kmp.shared.database.PlanetDao
import kotlinx.coroutines.flow.Flow

class PlanetRepository(private val planetDao: PlanetDao) {
    fun getAllPlanets(): Flow<List<PlanetEntity>> = planetDao.getAllPlanets()

    fun getVisiblePlanets(): Flow<List<PlanetEntity>> = planetDao.getVisiblePlanets()

    suspend fun insertPlanet(planet: PlanetEntity) {
        planetDao.insert(planet)
    }

    suspend fun updatePlanet(planet: PlanetEntity) {
        planetDao.update(planet)
    }

    suspend fun deletePlanet(planet: PlanetEntity) {
        planetDao.delete(planet)
    }

    suspend fun getPlanetById(id: Int): PlanetEntity? = planetDao.getPlanetById(id)
}
