package com.devmikespb.simpleweather.android.ui.screen.details

import com.devmikespb.simpleweather.android.ui.navigation.StringSerializer
import com.devmikespb.simpleweather.domain.entity.Place
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.domain.entity.TemperatureUnit
import org.json.JSONObject
import org.json.JSONTokener

data class PlaceDetailsScreenArguments(
    val place: Place,
    val weather: PlaceWeather,
) {
    companion object : StringSerializer<PlaceDetailsScreenArguments> {
        override fun toString(t: PlaceDetailsScreenArguments): String =
            JSONObject(
                mutableMapOf<String, Any?>(
                    KEY_PLACE_NAME to t.place.name,
                    KEY_TEMPERATURE to t.weather.temperature,
                    KEY_TEMPERATURE_UNIT to t.weather.temperatureUnit.name,
                )
            )
                .toString()

        override fun fromString(string: String): PlaceDetailsScreenArguments =
            (JSONTokener(string).nextValue() as JSONObject)
                .let { jsonObject ->
                    PlaceDetailsScreenArguments(
                        place = Place(jsonObject.getString(KEY_PLACE_NAME)),
                        weather = PlaceWeather(
                            temperature = jsonObject.getDouble(KEY_TEMPERATURE),
                            temperatureUnit = TemperatureUnit.valueOf(jsonObject.getString(KEY_TEMPERATURE_UNIT))
                        )
                    )
                }

        private const val KEY_PLACE_NAME = "KEY_PLACE_NAME"
        private const val KEY_TEMPERATURE = "KEY_TEMPERATURE"
        private const val KEY_TEMPERATURE_UNIT = "KEY_TEMPERATURE_UNIT"
    }
}
