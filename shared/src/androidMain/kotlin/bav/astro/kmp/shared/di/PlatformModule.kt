package bav.astro.kmp.shared.di

import androidx.room.Room
import bav.astro.kmp.shared.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single {
        val context = androidContext()
        val dbFile = context.getDatabasePath("astro.db")
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
    }
}
