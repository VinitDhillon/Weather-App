package com.example.wetherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitInstance {
    private const val baseUrl = "https://api.weatherapi.com";
    private fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val wetherApi : WeatherApi = getInstance().create(WeatherApi::class.java)
}