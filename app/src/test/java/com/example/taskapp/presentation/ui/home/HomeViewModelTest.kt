package com.example.taskapp.presentation.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.taskapp.MainCoroutineRule
import com.example.taskapp.data.remote.responses.ShortUrlResponse
import com.example.taskapp.repositories.FakeShortUrlRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(FakeShortUrlRepository())
    }

    @Test
    fun `insert null response in db gives error`() {
        viewModel.insertEntryInDB(null)

        assertThat(viewModel.uiState.value.error).isNotEmpty()
    }

    @Test
    fun `insert valid response in db action is successful`() {
        viewModel.insertEntryInDB(ShortUrlResponse())

        assertThat(viewModel.uiState.value.error).isNull()
    }

    @Test
    fun `empty string as url for conversion gives error`() {
        viewModel.convertUrl("")

        assertThat(viewModel.uiState.value.error).isNotEmpty()
    }
}