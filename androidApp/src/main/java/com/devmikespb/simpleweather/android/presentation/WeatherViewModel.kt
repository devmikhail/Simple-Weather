package com.devmikespb.simpleweather.android.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmikespb.simpleweather.mvi.Store
import com.devmikespb.simpleweather.presentation.main.WeatherStore
import com.devmikespb.simpleweather.presentation.main.WeatherStore.Action
import com.devmikespb.simpleweather.presentation.main.WeatherStore.State
import com.devmikespb.simpleweather.presentation.main.WeatherUpdater
import kotlinx.coroutines.flow.StateFlow

class WeatherViewModel(
    updater: WeatherUpdater,
) : ViewModel(),
    Store<Action, State> {

    private val store: WeatherStore = WeatherStore.create(
        initialState = State(),
        updater = updater,
        coroutineScope = viewModelScope,
    )

    override suspend fun dispatch(action: Action) {
        store.dispatch(action)
    }

    override val stateFlow: StateFlow<State> =
        store.stateFlow
}
