package com.example.weatherapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?&appid=5f082505d806b73532be251f0aa49354")
    suspend fun getCityWeather(
        @Query("lat") lan: Double,
        @Query("lon") lon: Double,
    ): Response<WeatherResponse>?
}