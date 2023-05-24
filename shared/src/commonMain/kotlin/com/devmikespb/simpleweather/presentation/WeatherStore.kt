package com.devmikespb.simpleweather.presentation

import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.mvi.Reducer
import com.devmikespb.simpleweather.mvi.Store
import com.devmike.core.mvi.StoreImpl
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import kotlinx.coroutines.CoroutineScope

class WeatherStore private constructor(
    private val storeImpl: StoreImpl<Action, State>,
) : ActionDispatcher<WeatherStore.Action> by storeImpl,
    Store<WeatherStore.State> by storeImpl {

    data class State(
        val cityName: String? = null,
        val cityWeather: PlaceWeather? = null,
        val isCityWeatherLoading: Boolean = false,
    )

    sealed class Action {
        data class UpdateCityName(val cityName: String): Action()
    }

    companion object {
        fun create(
            initialState: State,
            reducer: Reducer<State>,
            coroutineScope: CoroutineScope,
        ) : WeatherStore {
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
