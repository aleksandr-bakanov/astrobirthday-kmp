package bav.astro.kmp.shared.di

import androidx.room.Room
import bav.astro.kmp.shared.database.AppDatabase
import bav.astro.kmp.shared.database.AppDatabaseConstructor
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module

@OptIn(ExperimentalForeignApi::class)
actual val platformModule = module {
    single {
        val dbFilePath = documentDirectory() + "/astro.db"
        Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
            factory = { AppDatabaseConstructor.initialize() }
        )
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
    return documentDirectory?.path ?: ""
}
