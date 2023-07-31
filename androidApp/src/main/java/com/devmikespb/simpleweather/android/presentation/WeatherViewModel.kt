package com.devmikespb.simpleweather.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.mvi.Store
import com.devmikespb.simpleweather.presentation.main.WeatherReducer
import com.devmikespb.simpleweather.presentation.main.WeatherStore
import kotlinx.coroutines.flow.StateFlow

class WeatherViewModel(
    reducer: WeatherReducer,
) : ViewModel(),
    ActionDispatcher<WeatherStore.Action>,
    Store<WeatherStore.State> {

    private val store: WeatherStore = WeatherStore.create(
        initialState = WeatherStore.State(),
        reducer = reducer,
        coroutineScope = viewModelScope,
    )

    override suspend fun dispatch(action: WeatherStore.Action) {
        store.dispatch(action)
    }

    override val stateFlow: StateFlow<WeatherStore.State> =
        store.stateFlow
}
