package com.devmikespb.simpleweather.mvi

import kotlinx.coroutines.flow.StateFlow

interface Store<STATE> {
    val stateFlow: StateFlow<STATE>
}
