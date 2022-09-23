package com.example.taskapp.repositories

import androidx.lifecycle.LiveData
import com.example.taskapp.common.Resource
import com.example.taskapp.data.local.ShortURL
import com.example.taskapp.data.remote.responses.ShortUrlResponse

interface ShortUrlRepository {

    suspend fun insertUrl(shortURL: ShortURL)

    suspend fun deleteUrlEntry(shortURL: ShortURL)

    fun observeAllEntries(): LiveData<List<ShortURL>>

    suspend fun convertUrl(urlString: String): Resource<ShortUrlResponse>
}