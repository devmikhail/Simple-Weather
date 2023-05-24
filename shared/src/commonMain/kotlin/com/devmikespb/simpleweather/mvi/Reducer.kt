package com.devmikespb.simpleweather.mvi

interface Reducer<STATE> {
    suspend fun reduce(
        action: Any,
        state: STATE,
        updateState: (STATE.() -> (STATE)) -> Unit,
    )
}
