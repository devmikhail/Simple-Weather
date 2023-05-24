package com.devmikespb.simpleweather.mvi

interface ActionDispatcher<ACTION> {
    suspend fun dispatch(action: ACTION)
}
