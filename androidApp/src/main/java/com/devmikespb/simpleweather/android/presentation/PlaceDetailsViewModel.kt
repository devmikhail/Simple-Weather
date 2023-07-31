package com.devmikespb.simpleweather.android.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmikespb.simpleweather.android.ui.navigation.argument.StringSerializableArgument
import com.devmikespb.simpleweather.android.ui.screen.details.PlaceDetailsScreenArguments
import com.devmikespb.simpleweather.mvi.Store
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.Action
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.State
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsUpdater
import kotlinx.coroutines.flow.StateFlow


class PlaceDetailsViewModel(
    reducer: PlaceDetailsUpdater,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), Store<Action, State> {

    private val store = PlaceDetailsStore.create(
        initialState = StringSerializableArgument(PlaceDetailsScreenArguments)
            .restoreFromSavedState(savedStateHandle)
            .let {
                State(it.place, it.weather)
            },
        updater = reducer,
        coroutineScope = viewModelScope,
    )

    override suspend fun dispatch(action: Action) {
        store.dispatch(action)
    }

    override val stateFlow: StateFlow<State> =
        store.stateFlow
}
