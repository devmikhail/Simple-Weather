package com.devmikespb.simpleweather.presentation.details

import co.touchlab.kermit.Logger
import com.devmikespb.simpleweather.mvi.HandleActionException
import com.devmikespb.simpleweather.mvi.Reducer
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.Action
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.State

class PlaceDetailsReducer : Reducer<State> {
    override suspend fun reduce(
        action: Any,
        state: State,
        updateState: (State.() -> State) -> Unit,
    ) {
        when (action) {
            is Action.ExecuteCommand -> {
                updateState {
                    Logger.d { "Action.ExecuteCommand - updateState" }
                    copy(commandToExecute = action.command)
                }
            }
            is Action.CommandWasExecuted -> {
                updateState {
                    Logger.d { "Action.CommandWasExecuted - updateState" }
                    if (commandToExecute == action.command) {
                        copy(commandToExecute = null)
                    } else this
                }
            }
            is HandleActionException -> {
                Logger.d { "HandleActionException" }
                /* TODO handle exception in state */
                Logger.e { "Got exception: $action" }
            }
        }
    }
}
