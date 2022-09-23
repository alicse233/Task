package com.example.taskapp.data.remote

import com.example.taskapp.data.remote.responses.ShortUrlResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TaskAppAPI {

    @GET("/v2/shorten")
    suspend fun shortenURL(
        @Query("url") url: String
    ): Response<ShortUrlResponse>
}