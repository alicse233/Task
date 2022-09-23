package com.example.taskapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskapp.common.Resource
import com.example.taskapp.data.local.ShortURL
import com.example.taskapp.data.remote.responses.ShortUrlResponse

class FakeShortUrlRepository: ShortUrlRepository {

    private val urlEntries = mutableListOf<ShortURL>()

    private val observableUrls = MutableLiveData<List<ShortURL>>(urlEntries)

    private var shouldReturnNetworkError: Boolean = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableUrls.postValue(urlEntries)
    }

    override suspend fun insertUrl(shortURL: ShortURL) {
        urlEntries.add(shortURL)
        refreshLiveData()
    }

    override suspend fun deleteUrlEntry(shortURL: ShortURL) {
        urlEntries.remove(shortURL)
        refreshLiveData()
    }

    override fun observeAllEntries(): LiveData<List<ShortURL>> {
        return observableUrls
    }

    override suspend fun convertUrl(urlString: String): Resource<ShortUrlResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ShortUrlResponse())
        }
    }
}