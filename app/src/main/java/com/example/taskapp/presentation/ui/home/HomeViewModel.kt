package com.example.taskapp.presentation.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.common.Status
import com.example.taskapp.data.local.ShortURL
import com.example.taskapp.data.local.toShortUrl
import com.example.taskapp.data.remote.responses.ShortUrlResponse
import com.example.taskapp.repositories.ShortUrlRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val urlRepository: ShortUrlRepository
) : ViewModel() {

    val localUrlEntries = urlRepository.observeAllEntries()

    private val _uiState = mutableStateOf(HomeUIState())
    val uiState = _uiState

    fun convertUrl(urlString: String) {
        if (urlString.isNotBlank()) {
            viewModelScope.launch {
                _uiState.value = HomeUIState(isLoading = true)
                val resp = urlRepository.convertUrl(urlString)
                when (resp.status) {
                    Status.SUCCESS -> {
                        _uiState.value = HomeUIState(isLoading = false)
                        if (resp.data != null) {
                            insertEntryInDB(resp.data)
                        }
                    }
                    Status.ERROR -> {
                        _uiState.value =
                            HomeUIState(error = resp.message ?: "unknown error occurred :(")
                    }
                    Status.LOADING -> {
                        _uiState.value = HomeUIState(isLoading = true)
                    }
                }
            }
        } else {
            _uiState.value = HomeUIState(error = "Please enter a valid URL!")
        }
    }

    fun insertEntryInDB(shortURL: ShortUrlResponse?) {
        if (shortURL != null) {
            viewModelScope.launch {
                urlRepository.insertUrl(shortURL.toShortUrl())
            }
        } else {
            _uiState.value = HomeUIState(error = "No response from server!")
        }
    }

    fun updateEntryInDB(shortURL: ShortURL?) {
        if (shortURL != null) {
            viewModelScope.launch {
                var previousSelection: ShortURL? = null
                localUrlEntries.value?.forEach {
                    if (it.isCopied) {
                        previousSelection = it
                        return@forEach
                    }
                }
                if (previousSelection != null) urlRepository.insertUrl(
                    previousSelection!!.copy(
                        isCopied = false
                    )
                )
                urlRepository.insertUrl(shortURL)
            }
        } else {
            _uiState.value = HomeUIState(error = "Error occurred while updating!")
        }
    }

    fun deleteEntryInDB(shortURL: ShortURL) {
        viewModelScope.launch {
            urlRepository.deleteUrlEntry(shortURL)
        }
    }

    fun onResultConsumed() {
        _uiState.value = HomeUIState()
    }

}