package bav.astro.kmp.shared.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val birthday: String // format dd-mm-yyyy
)
