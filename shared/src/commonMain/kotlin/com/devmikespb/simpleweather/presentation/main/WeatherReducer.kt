package com.devmikespb.simpleweather.presentation.main

import co.touchlab.kermit.Logger
import com.devmikespb.simpleweather.domain.repository.PlaceWeatherRepository
import com.devmikespb.simpleweather.mvi.HandleActionException
import com.devmikespb.simpleweather.mvi.Reducer

class WeatherReducer(
    private val placeWeatherRepository: PlaceWeatherRepository
) : Reducer<WeatherStore.State> {

    override suspend fun reduce(
        action: Any,
        state: WeatherStore.State,
        updateState: (WeatherStore.State.() -> WeatherStore.State) -> Unit,
    ) {
        when (action) {
            is WeatherStore.Action.UpdateCityName -> {
                updateState { copy(cityName = action.cityName, isCityWeatherLoading = true) }
                val placeWeather = placeWeatherRepository.getPlaceWeatherByCityName(action.cityName)
                updateState { copy(isCityWeatherLoading = false, cityWeather = placeWeather) }
            }
            is WeatherStore.Action.ExecuteCommand -> {
                updateState { copy(commandToExecute = action.command) }
            }
            is WeatherStore.Action.CommandWasExecuted -> {
                updateState {
                    if (commandToExecute == action.command) {
                        copy(commandToExecute = null)
                    } else this
                }
            }
            is HandleActionException -> {
                /* TODO handle exception in state */
                Logger.e { "Got exception: $action" }
            }
        }
    }
}
