package com.devmikespb.simpleweather.android.ui

import com.devmikespb.simpleweather.domain.entity.TemperatureUnit
import com.devmikespb.simpleweather.mvi.ActionDispatcher

fun formatTemperature(temperature: Double?): String =
    temperature?.toString()?: "n/a"

fun formatTemperatureUnit(temperatureUnit: TemperatureUnit?): String =
    when(temperatureUnit) {
        TemperatureUnit.Celsius -> "°C"
        TemperatureUnit.Fahrenheit -> "°F"
        null -> "n/a"
    }

class StubDispatcher<ACTION> : ActionDispatcher<ACTION> {
    override suspend fun dispatch(action: ACTION) {}
}
