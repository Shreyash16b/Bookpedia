package com.shreyash16b.bookpedia.book.data.database
import platform.Foundation.NSDocumentDirectory
import androidx.room.RoomDatabase

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavouriteBookDatabase> {
        val dbFile = documentDirectory()
    }

    private fun documentDirectory() : String {
        val documentDirectory  = NSFileManager.default
    }
}