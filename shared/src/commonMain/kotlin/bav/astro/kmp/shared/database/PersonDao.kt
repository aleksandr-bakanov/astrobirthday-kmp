package bav.astro.kmp.shared.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert
    suspend fun insert(person: Person)

    @Update
    suspend fun update(person: Person)

    @Query("SELECT * FROM Person")
    fun getAllPeople(): Flow<List<Person>>

    @Query("SELECT * FROM Person WHERE id = :id")
    suspend fun getPersonById(id: Int): Person?
}
