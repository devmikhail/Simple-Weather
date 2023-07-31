package com.devmikespb.simpleweather.presentation.details

import com.devmike.core.mvi.StoreImpl
import com.devmikespb.simpleweather.domain.entity.Place
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.mvi.Reducer
import com.devmikespb.simpleweather.mvi.Store
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.Action
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.State
import kotlinx.coroutines.CoroutineScope

class PlaceDetailsStore private constructor(
    private val storeImpl: StoreImpl<Action, State>,
) : ActionDispatcher<Action> by storeImpl,
    Store<State> by storeImpl {
    data class State(
        val place: Place,
        val placeWeather: PlaceWeather,
        val commandToExecute: Command? = null,
    )

    sealed interface Command {
        object Exit : Command
    }

    sealed interface Action {
        data class ExecuteCommand(val command: Command) : Action
        data class CommandWasExecuted(val command: Command) : Action
    }

    companion object {
        fun create(
            initialState: State,
            reducer: Reducer<State>,
            coroutineScope: CoroutineScope,
        ): PlaceDetailsStore =
            StoreImpl<Action, State>(
                initialState = initialState,
                initialActions = emptyList(),
                coroutineScope = coroutineScope,
                reducer = reducer,
            )
                .let { PlaceDetailsStore(it) }
    }
}
