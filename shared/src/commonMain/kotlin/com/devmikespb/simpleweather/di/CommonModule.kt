package com.devmikespb.simpleweather.di

import com.devmikespb.simpleweather.data.network.WeatherApi
import com.devmikespb.simpleweather.data.repository.PlaceWeatherRepositoryImpl
import com.devmikespb.simpleweather.domain.repository.PlaceWeatherRepository
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsUpdater
import com.devmikespb.simpleweather.presentation.main.WeatherUpdater
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commonModule = module {
    single { WeatherApi(get(named("apiKey"))) }
    single<PlaceWeatherRepository> { PlaceWeatherRepositoryImpl(get()) }

    factory { WeatherUpdater(get()) }
    factory { PlaceDetailsUpdater() }
}
