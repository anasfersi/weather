package com.example.weatherapp.ui.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.data.LIST_OF_CITIES
import com.example.weatherapp.data.WAITING_MESSAGES
import com.example.weatherapp.data.model.ListItem
import com.example.weatherapp.data.remote.RetrofitHelper
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.ui.extensions.toCelsius
import com.example.weatherapp.ui.extensions.toWeatherIconImageUrl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SecondFragmentViewModel : ViewModel() {

    data class UiState(
        val progressValue: Int = 0,
        val waitingText: Int = WAITING_MESSAGES[0],
        val item: ListItem? = null,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val weatherApi: WeatherApi = RetrofitHelper.getInstance().create(WeatherApi::class.java)


    init {
        startCycle()
    }

    private fun startCycle() {
        viewModelScope.launch {
            while (_uiState.value.progressValue < 100) {
                delay(6000)
                updateMessage()
                delay(4000)
                updateProgress()
            }
        }
    }

    private fun updateProgress() {
        getCityWeather()
        _uiState.update { currentState ->
            currentState.copy(
                progressValue = currentState.progressValue + 20,
            )
        }
    }

    private fun updateMessage() {
        _uiState.update { currentState ->
            currentState.copy(
                waitingText = getNextWaitingMessage()
            )
        }
    }


    private var msgPos = 0
    private fun getNextWaitingMessage(): Int {
        if (msgPos < 2)
            msgPos++
        else msgPos = 0
        return WAITING_MESSAGES[msgPos]
    }

    fun resetProgress() {
        idx = 0
        msgPos = 0
        _uiState.update { currentState ->
            currentState.copy(
                progressValue = 0,
                waitingText = WAITING_MESSAGES[msgPos]
            )
        }
        startCycle()
    }

    private var idx = 0
    private fun getCityWeather() {
        if (idx > LIST_OF_CITIES.size) return
        val city = LIST_OF_CITIES[idx]
        viewModelScope.launch {
            try {
                val result = weatherApi.getCityWeather(city.lat, city.lon)
                if (result != null && result.isSuccessful) {
                    if (result.body() != null) {
                        val body = result.body()!!
                        val weather = body.weather.first()
                        _uiState.update { currentState ->
                            currentState.copy(
                                item = ListItem(
                                    body.name,
                                    weather.icon.toWeatherIconImageUrl(),
                                    body.main.temp.toCelsius().toFloat().toString()
                                )
                            )
                        }
                    }
                }
                idx++
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        waitingText = R.string.loading_error
                    )
                }
                println("Error : $e")
            }
        }
    }

}
