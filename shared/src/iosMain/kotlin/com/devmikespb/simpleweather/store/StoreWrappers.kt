package com.devmikespb.simpleweather.store

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class StoreWrappers : KoinComponent {
    fun weatherStoreWrapper() : WeatherStoreWrapper = get()
}
