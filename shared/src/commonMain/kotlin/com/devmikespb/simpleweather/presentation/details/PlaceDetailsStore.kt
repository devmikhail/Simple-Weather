package com.devmikespb.simpleweather.presentation.details

import com.devmikespb.simpleweather.domain.entity.Place
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.mvi.ElmLikeStoreImpl
import com.devmikespb.simpleweather.mvi.Store
import com.devmikespb.simpleweather.mvi.Updater
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.Action
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.State
import kotlinx.coroutines.CoroutineScope

class PlaceDetailsStore private constructor(
    private val storeImpl: ElmLikeStoreImpl<Action, State>,
) : Store<Action, State> by storeImpl {
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
            updater: Updater<Action, State>,
            coroutineScope: CoroutineScope,
        ): PlaceDetailsStore =
            PlaceDetailsStore(
                ElmLikeStoreImpl(
                    initialState = initialState,
                    initialActions = emptyList(),
                    coroutineScope = coroutineScope,
                    updater = updater,
                )
            )
    }
}
