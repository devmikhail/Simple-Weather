package com.devmikespb.simpleweather.presentation.main

import com.devmikespb.simpleweather.domain.entity.Place
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.mvi.ElmLikeStoreImpl
import com.devmikespb.simpleweather.mvi.Store
import com.devmikespb.simpleweather.presentation.main.WeatherStore.Action
import com.devmikespb.simpleweather.presentation.main.WeatherStore.State
import kotlinx.coroutines.CoroutineScope

class WeatherStore private constructor(
    private val storeImpl: ElmLikeStoreImpl<Action, State>,
) : Store<Action, State> by storeImpl {

    data class State(
        val cityName: String? = null,
        val cityWeather: PlaceWeather? = null,
        val isCityWeatherLoading: Boolean = false,
        val commandToExecute: Command? = null,
    )

    sealed interface Action {
        data class UpdateCityName(val cityName: String) : Action
        data class PlaceWeatherOutcome(val result: Result<PlaceWeather?>) : Action
        data class ExecuteCommand(val command: Command) : Action
        data class CommandWasExecuted(val command: Command) : Action
    }

    sealed interface Command {
        data class DetailsScreen(val place: Place, val placeWeather: PlaceWeather) : Command
    }

    companion object {
        fun create(
            initialState: State,
            updater: WeatherUpdater,
            coroutineScope: CoroutineScope,
        ): WeatherStore =
            WeatherStore(
                storeImpl = ElmLikeStoreImpl(
                    initialState = initialState,
                    initialActions = emptyList(),
                    coroutineScope = coroutineScope,
                    updater = updater,
                )
            )
    }
}
