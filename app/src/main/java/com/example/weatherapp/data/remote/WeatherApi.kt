package com.example.weatherapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "5f082505d806b73532be251f0aa49354"

interface WeatherApi {
    @GET("weather?&appid=$API_KEY")
    suspend fun getCityWeather(
        @Query("lat") lan: Double,
        @Query("lon") lon: Double,
    ): Response<WeatherResponse>?
}