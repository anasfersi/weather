package com.example.weatherapp.data.remote

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherState

data class WeatherResponse(
    val name: String,
    val main: Weather,
    val weather: List<WeatherState>
)