package com.devmikespb.simpleweather.presentation.main

import com.devmikespb.simpleweather.domain.repository.PlaceWeatherRepository
import com.devmikespb.simpleweather.mvi.Updater
import com.devmikespb.simpleweather.mvi.Updater.Update
import com.devmikespb.simpleweather.mvi.executeAsEffect
import com.devmikespb.simpleweather.presentation.main.WeatherStore.Action
import com.devmikespb.simpleweather.presentation.main.WeatherStore.State

class WeatherUpdater(
    private val placeWeatherRepository: PlaceWeatherRepository,
) : Updater<Action, State> {

    override fun update(action: Action, state: State): Update<Action, State> =
        when (action) {
            is Action.UpdateCityName -> {
                Update(
                    state.copy(cityName = action.cityName, isCityWeatherLoading = true),
                    executeAsEffect(Action::PlaceWeatherOutcome) {
                        placeWeatherRepository.getPlaceWeatherByCityName(action.cityName)
                    }
                )
            }
            is Action.PlaceWeatherOutcome -> {
                action.result.fold(
                    onSuccess = { placeWeather ->
                        Update(state.copy(isCityWeatherLoading = false, cityWeather = placeWeather))
                    },
                    onFailure = {
                        Update(state.copy(isCityWeatherLoading = false))
                    }
                )
            }
            is Action.ExecuteCommand -> {
                Update(state.copy(commandToExecute = action.command))
            }
            is Action.CommandWasExecuted -> {
                if (state.commandToExecute == action.command) {
                    Update(state.copy(commandToExecute = null))
                } else {
                    Update()
                }
            }
        }

    override fun onUnhandledError(th: Throwable, state: State): State {
        /* TODO handle unknown exception in update */
        throw th
    }
}
