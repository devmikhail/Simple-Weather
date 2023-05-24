package com.devmikespb.simpleweather.domain.repository

import com.devmikespb.simpleweather.domain.entity.PlaceWeather

interface PlaceWeatherRepository {
    suspend fun getPlaceWeatherByCityName(cityName: String): PlaceWeather?
}
