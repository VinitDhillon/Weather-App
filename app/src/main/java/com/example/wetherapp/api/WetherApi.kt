package com.example.wetherapp.api
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/current.json")
    suspend fun getWether(

        @Query("key")
        apiKey: String,

        @Query("q")
        city: String

    ): Response<WetherModel>

}

