package bav.astro.kmp.shared.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import bav.astro.kmp.shared.database.AppDatabase
import bav.astro.kmp.shared.database.AppDatabaseConstructor
import bav.astro.kmp.shared.datastore.createDataStore
import bav.astro.kmp.shared.datastore.dataStoreFileName
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val platformModule = module {
    single {
        val documentDirectory = documentDirectory()
        val dbFilePath = "$documentDirectory/astro.db"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
            factory = { AppDatabaseConstructor.initialize() }
        )
    }

    single<DataStore<Preferences>> {
        createDataStore()
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    val path = documentDirectory?.path
    requireNotNull(path)
    return path
}

@OptIn(ExperimentalForeignApi::class)
private fun createDataStore(): DataStore<Preferences> = createDataStore(
    producePath = {
        val documentDirectory = documentDirectory()
        "$documentDirectory/$dataStoreFileName"
    }
)
