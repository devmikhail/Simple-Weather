package com.devmikespb.simpleweather.mvi

import kotlinx.coroutines.flow.Flow

interface Updater<ACTION, STATE> {
    class Update<ACTION, STATE>(val state: STATE? = null, val sideEffect: Flow<ACTION>? = null) {
        constructor(sideEffect: Flow<ACTION>) : this(null, sideEffect)
    }

    fun update(action: ACTION, state: STATE): Update<ACTION, STATE>

    fun onUnhandledError(th: Throwable, state: STATE): STATE
}
