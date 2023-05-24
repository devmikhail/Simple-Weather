package com.devmikespb.simpleweather.data.dto

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class Weather(
    @SerialName("id") val id: Int,
    @SerialName("coord") val coordinates: Coordinates,
    @SerialName("main") val mainData: WeatherMainData,
)

@kotlinx.serialization.Serializable
data class Coordinates(
    @SerialName("lat") val latitude: Double,
    @SerialName("lon") val longitude: Double,
)

@kotlinx.serialization.Serializable
data class WeatherMainData(
    @SerialName("temp") val temperature: Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double,
)
