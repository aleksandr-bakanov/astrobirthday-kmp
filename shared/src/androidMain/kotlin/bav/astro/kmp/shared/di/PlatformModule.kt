package bav.astro.kmp.shared.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import bav.astro.kmp.shared.database.AppDatabase
import bav.astro.kmp.shared.datastore.createDataStore
import bav.astro.kmp.shared.datastore.dataStoreFileName
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

    single<DataStore<Preferences>> {
        createDataStore(context = androidContext())
    }
}

private fun createDataStore(context: Context): DataStore<Preferences> =
    createDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
