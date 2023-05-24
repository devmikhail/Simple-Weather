package com.devmikespb.simpleweather.data.repository

import com.devmikespb.simpleweather.data.network.WeatherApi
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.domain.entity.TemperatureUnit
import com.devmikespb.simpleweather.domain.repository.PlaceWeatherRepository

class PlaceWeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
) : PlaceWeatherRepository {

    override suspend fun getPlaceWeatherByCityName(cityName: String): PlaceWeather? =
        weatherApi
            .fetchWeather(city = cityName)
            .let { weather ->
                weather?.let {
                    PlaceWeather(
                        temperature = it.mainData.temperature,
                        temperatureUnit = TemperatureUnit.Celsius
                    )
                }
            }
}
