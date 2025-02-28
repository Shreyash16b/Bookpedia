package com.shreyash16b.bookpedia.book.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create() : RoomDatabase.Builder<FavouriteBookDatabase>
}