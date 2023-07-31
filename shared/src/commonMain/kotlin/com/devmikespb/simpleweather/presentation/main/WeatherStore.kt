package com.devmikespb.simpleweather.presentation.main

import com.devmike.core.mvi.StoreImpl
import com.devmikespb.simpleweather.domain.entity.Place
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.mvi.Reducer
import com.devmikespb.simpleweather.mvi.Store
import kotlinx.coroutines.CoroutineScope

class WeatherStore private constructor(
    private val storeImpl: StoreImpl<Action, State>,
) : ActionDispatcher<WeatherStore.Action> by storeImpl,
    Store<WeatherStore.State> by storeImpl {

    data class State(
        val cityName: String? = null,
        val cityWeather: PlaceWeather? = null,
        val isCityWeatherLoading: Boolean = false,
        val isShowTestScreen: Boolean = false,
        val commandToExecute: Command? = null,
    )

    sealed interface Action {
        data class UpdateCityName(val cityName: String) : Action
        data class ExecuteCommand(val command: Command) : Action
        data class CommandWasExecuted(val command: Command) : Action
    }

    sealed interface Command {
        data class DetailsScreen(val place: Place, val placeWeather: PlaceWeather) : Command
    }

    companion object {
        fun create(
            initialState: State,
            reducer: Reducer<State>,
            coroutineScope: CoroutineScope,
        ): WeatherStore {
            val storeImpl = StoreImpl<Action, State>(
                initialState = initialState,
                initialActions = emptyList(),
                coroutineScope = coroutineScope,
                reducer = reducer,
            )
            return WeatherStore(storeImpl = storeImpl)
        }
    }
}
