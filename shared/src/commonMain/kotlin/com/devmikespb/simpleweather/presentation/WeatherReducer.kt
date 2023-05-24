package com.devmikespb.simpleweather.presentation

import co.touchlab.kermit.Logger
import com.devmikespb.simpleweather.mvi.Reducer
import com.devmikespb.simpleweather.domain.repository.PlaceWeatherRepository
import com.devmikespb.simpleweather.mvi.HandleActionException

class WeatherReducer(
    private val placeWeatherRepository: PlaceWeatherRepository
) : Reducer<WeatherStore.State> {

    override suspend fun reduce(
        action: Any,
        state: WeatherStore.State,
        updateState: (WeatherStore.State.() -> WeatherStore.State) -> Unit,
    ) {
        when(action) {
            is WeatherStore.Action.UpdateCityName -> {
                updateState { copy(cityName = cityName, isCityWeatherLoading = true) }
                val placeWeather = placeWeatherRepository.getPlaceWeatherByCityName(action.cityName)
                updateState { copy(isCityWeatherLoading = false, cityWeather = placeWeather) }
            }
            is HandleActionException -> {
                /* TODO handle exception in state */
                Logger.e { "Got exception: $action" }
            }
        }
    }
}
