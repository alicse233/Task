package com.example.taskapp.repositories

import androidx.lifecycle.LiveData
import com.example.taskapp.common.Resource
import com.example.taskapp.data.local.ShortURL
import com.example.taskapp.data.local.ShortURLDao
import com.example.taskapp.data.remote.TaskAppAPI
import com.example.taskapp.data.remote.responses.ShortUrlResponse
import javax.inject.Inject

class ShortUrlRepositoryImpl @Inject constructor(
    private val shortURLDao: ShortURLDao,
    private val appAPI: TaskAppAPI
) : ShortUrlRepository {

    override suspend fun insertUrl(shortURL: ShortURL) {
        shortURLDao.insertShortUrls(shortURL)
    }

    override suspend fun deleteUrlEntry(shortURL: ShortURL) {
        shortURLDao.deleteShortURL(shortURL)
    }

    override fun observeAllEntries(): LiveData<List<ShortURL>> {
        return shortURLDao.observeAllURLS()
    }

    override suspend fun convertUrl(urlString: String): Resource<ShortUrlResponse> {
        return try {
            val response = appAPI.shortenURL(urlString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                return Resource.error("unable to shorten this link :(", null)
            }
        } catch (exception: Exception) {
            return Resource.error("unable to reach to server please check your internet", null)
        }
    }
}