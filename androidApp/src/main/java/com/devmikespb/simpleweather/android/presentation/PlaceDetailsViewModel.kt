package com.devmikespb.simpleweather.android.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmikespb.simpleweather.android.ui.navigation.argument.StringSerializableArgument
import com.devmikespb.simpleweather.android.ui.screen.details.PlaceDetailsScreenArguments
import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.mvi.Store
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsReducer
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.Action
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.State
import kotlinx.coroutines.flow.StateFlow


class PlaceDetailsViewModel(
    reducer: PlaceDetailsReducer,
    savedStateHandle: SavedStateHandle,
) : ViewModel(),
    ActionDispatcher<Action>,
    Store<State> {

    private val store = PlaceDetailsStore.create(
        initialState = StringSerializableArgument(PlaceDetailsScreenArguments)
            .restoreFromSavedState(savedStateHandle)
            .let {
                State(it.place, it.weather)
            },
        reducer = reducer,
        coroutineScope = viewModelScope,
    )

    override suspend fun dispatch(action: Action) {
        store.dispatch(action)
    }

    override val stateFlow: StateFlow<State> =
        store.stateFlow
}
