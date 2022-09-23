package com.example.taskapp.di

import android.content.Context
import androidx.room.Room
import com.example.taskapp.common.AppConstants.APP_DATABASE
import com.example.taskapp.common.AppConstants.BASE_URL
import com.example.taskapp.data.local.AppDatabase
import com.example.taskapp.data.local.ShortURLDao
import com.example.taskapp.data.remote.TaskAppAPI
import com.example.taskapp.repositories.ShortUrlRepository
import com.example.taskapp.repositories.ShortUrlRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        APP_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideDao(
        database: AppDatabase
    ) = database.urlsDao()

    @Singleton
    @Provides
    fun provideRetrofit(): TaskAppAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(TaskAppAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        appAPI: TaskAppAPI,
        urlDao: ShortURLDao
    ): ShortUrlRepository {
        return ShortUrlRepositoryImpl(urlDao, appAPI)
    }
}