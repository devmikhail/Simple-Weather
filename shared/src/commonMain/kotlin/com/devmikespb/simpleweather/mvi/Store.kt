package com.devmikespb.simpleweather.mvi


interface Store<ACTION, STATE> : ActionDispatcher<ACTION>, StateHolder<STATE>
