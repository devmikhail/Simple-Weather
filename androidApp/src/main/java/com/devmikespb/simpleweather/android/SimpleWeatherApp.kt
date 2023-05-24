package com.devmikespb.simpleweather.android

import android.app.Application
import com.devmikespb.simpleweather.android.di.viewModelModule
import com.devmikespb.simpleweather.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class SimpleWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            apiKey = BuildConfig.API_KEY,
            additionalSettings = {
                androidLogger()
                androidContext(this@SimpleWeatherApp)
            },
            additionalModules = listOf(viewModelModule),
        )
    }
}
