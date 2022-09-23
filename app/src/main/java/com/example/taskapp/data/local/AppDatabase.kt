package com.example.taskapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShortURL::class],
    version = 3
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun urlsDao(): ShortURLDao

}