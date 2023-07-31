package com.devmikespb.simpleweather.presentation.details

import com.devmikespb.simpleweather.mvi.Updater
import com.devmikespb.simpleweather.mvi.Updater.Update
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.Action
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.State


class PlaceDetailsUpdater : Updater<Action, State> {
    override fun update(action: Action, state: State): Update<Action, State> =
        when (action) {
            is Action.ExecuteCommand -> {
                Update(state.copy(commandToExecute = action.command))
            }
            is Action.CommandWasExecuted -> {
                if (state.commandToExecute == action.command) {
                    Update(state.copy(commandToExecute = null))
                } else {
                    Update()
                }
            }
        }

    override fun onUnhandledError(th: Throwable, state: State): State {
        /* TODO handle unknown exception in update */
        throw th
    }
}
