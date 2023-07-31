package com.devmikespb.simpleweather.store

import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.mvi.Store
import com.devmikespb.simpleweather.presentation.main.WeatherReducer
import com.devmikespb.simpleweather.presentation.main.WeatherStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow

class WeatherStoreWrapper(
    weatherReducer: WeatherReducer
): ActionDispatcher<WeatherStore.Action>,
    Store<WeatherStore.State> {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val store = WeatherStore.create(
        initialState = WeatherStore.State(),
        reducer = weatherReducer,
        coroutineScope = scope,
    )

    fun onCleared() {
        scope.cancel()
    }

    override suspend fun dispatch(action: WeatherStore.Action) {
        store.dispatch(action)
    }

    override val stateFlow: StateFlow<WeatherStore.State> =
        store.stateFlow
}
