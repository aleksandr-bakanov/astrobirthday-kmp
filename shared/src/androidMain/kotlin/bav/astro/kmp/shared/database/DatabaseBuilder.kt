package bav.astro.kmp.shared.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

// This needs to be initialized in the Application class
lateinit var appContext: Context

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = appContext.getDatabasePath("astro.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
