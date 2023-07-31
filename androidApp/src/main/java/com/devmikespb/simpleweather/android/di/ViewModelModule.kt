package com.devmikespb.simpleweather.android.di

import com.devmikespb.simpleweather.android.presentation.PlaceDetailsViewModel
import com.devmikespb.simpleweather.android.presentation.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
    viewModel { PlaceDetailsViewModel(get(), get()) }
}
