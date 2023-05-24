package com.devmikespb.simpleweather.di

import org.koin.core.qualifier.named
import org.koin.dsl.module

fun constantsModule(apiKey: String) = module {
    single(named("apiKey")) { apiKey }
}
