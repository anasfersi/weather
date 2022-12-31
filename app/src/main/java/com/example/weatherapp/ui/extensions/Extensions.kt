package com.example.weatherapp.ui.extensions


fun Int.toPercentageString() = "$this %"

fun Double.toCelsius() = (this - 273.15)

const val IMG_URL = "https://openweathermap.org/img/wn/"
const val IMG_EXT = "@4x.png"

fun String.toWeatherIconImageUrl() = IMG_URL + this + IMG_EXT
