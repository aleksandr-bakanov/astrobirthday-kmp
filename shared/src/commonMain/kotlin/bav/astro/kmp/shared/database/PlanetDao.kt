package bav.astro.kmp.shared.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanetDao {
    @Insert
    suspend fun insert(planet: PlanetEntity)

    @Update
    suspend fun update(planet: PlanetEntity)

    @Delete
    suspend fun delete(planet: PlanetEntity)

    @Query("SELECT * FROM PlanetEntity")
    fun getAllPlanets(): Flow<List<PlanetEntity>>

    @Query("SELECT * FROM PlanetEntity WHERE isVisible = 1")
    fun getVisiblePlanets(): Flow<List<PlanetEntity>>

    @Query("SELECT * FROM PlanetEntity WHERE id = :id")
    suspend fun getPlanetById(id: Int): PlanetEntity?
}
