package bav.astro.kmp.shared.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlanetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val period: Double, // In Earth days
    val isVisible: Boolean,
)
