package bav.astro.kmp.shared.repository

import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.database.PersonDao
import kotlinx.coroutines.flow.Flow

class PersonRepository(private val personDao: PersonDao) {
    fun getAllPeople(): Flow<List<Person>> = personDao.getAllPeople()
    
    suspend fun insertPerson(person: Person) {
        personDao.insert(person)
    }

    suspend fun updatePerson(person: Person) {
        personDao.update(person)
    }

    suspend fun deletePerson(person: Person) {
        personDao.delete(person)
    }

    suspend fun getPersonById(id: Int): Person? = personDao.getPersonById(id)
}
