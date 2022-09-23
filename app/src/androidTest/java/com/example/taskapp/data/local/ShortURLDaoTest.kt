package com.example.taskapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.taskapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@HiltAndroidTest
class ShortURLDaoTest {

    @get:Rule var hiltRule = HiltAndroidRule(this)

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var db: AppDatabase

    private lateinit var shortURLDao: ShortURLDao

    @Before
    fun setUp() {
        hiltRule.inject()
        shortURLDao = db.urlsDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertURLEntry() = runBlockingTest {

        val url = ShortURL("fd", "ijf", "we", "sder", id = 1)
        shortURLDao.insertShortUrls(url)

        val allConversions = shortURLDao.observeAllURLS().getOrAwaitValue()

        assertThat(allConversions).contains(url)

    }

    @Test
    fun deleteEntry() = runBlockingTest {
        val url = ShortURL("sdf", "dsf", "dsa", "da", id = 1)
        shortURLDao.insertShortUrls(url)
        shortURLDao.deleteShortURL(url)

        val allEntries = shortURLDao.observeAllURLS().getOrAwaitValue()
        assertThat(allEntries).doesNotContain(url)
    }
}