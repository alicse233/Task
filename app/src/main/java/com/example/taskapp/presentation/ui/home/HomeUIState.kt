package com.example.taskapp.presentation.ui.home

data class HomeUIState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null
)