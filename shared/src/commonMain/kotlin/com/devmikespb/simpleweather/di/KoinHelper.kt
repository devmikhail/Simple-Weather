package com.devmikespb.simpleweather.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(
    apiKey: String,
    additionalModules: List<Module> = emptyList(),
    additionalSettings: (KoinApplication.() -> Unit) = {},
) {
    startKoin {
        additionalSettings()
        modules(
            mutableListOf(constantsModule(apiKey), commonModule)
                .apply { addAll(additionalModules) }
        )
    }
}
