package com.example.taskapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import retrofit2.http.DELETE

@Dao
interface ShortURLDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortUrls(vararg shortURL: ShortURL)

    @Delete
    suspend fun deleteShortURL(shortURL: ShortURL)

    @Query("SELECT * FROM short_urls")
    fun observeAllURLS(): LiveData<List<ShortURL>>
}