package com.devmikespb.simpleweather.di

import com.devmikespb.simpleweather.data.network.WeatherApi
import com.devmikespb.simpleweather.data.repository.PlaceWeatherRepositoryImpl
import com.devmikespb.simpleweather.domain.repository.PlaceWeatherRepository
import com.devmikespb.simpleweather.mvi.Reducer
import com.devmikespb.simpleweather.presentation.WeatherReducer
import com.devmikespb.simpleweather.presentation.WeatherStore
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {
    single { WeatherApi(get(named("apiKey"))) }
    single<PlaceWeatherRepository> { PlaceWeatherRepositoryImpl(get()) }
    factory { WeatherReducer(get()) }
}
