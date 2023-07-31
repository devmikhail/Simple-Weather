package com.devmikespb.simpleweather.mvi

import kotlinx.coroutines.flow.StateFlow

interface StateHolder<STATE> {
    val stateFlow: StateFlow<STATE>
}
