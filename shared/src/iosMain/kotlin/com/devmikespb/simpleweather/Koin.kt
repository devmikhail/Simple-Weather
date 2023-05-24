package com.devmikespb.simpleweather

import com.devmikespb.simpleweather.store.WeatherStoreWrapper
import org.koin.dsl.module

val storeWrappersModule = module {
    factory { WeatherStoreWrapper(get()) }
}
