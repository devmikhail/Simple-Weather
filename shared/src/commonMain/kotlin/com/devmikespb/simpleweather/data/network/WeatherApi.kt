package com.devmikespb.simpleweather.data.network

import co.touchlab.kermit.Logger
import com.devmikespb.simpleweather.data.dto.Weather
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class WeatherApi(
    private val apiKey: String,
) {
    private val baseUrl = "https://api.openweathermap.org/data/2.5/weather"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                }
            )
        }
    }

    @Throws(Throwable::class)
    suspend fun fetchWeather(city: String): Weather? =
        httpClient
            .get(baseUrl) {
                url {
                    parameters.append("appid", apiKey)
                    parameters.append("q", city)
                    parameters.append("units", "metric")
                }
            }
            .let {
                if (it.status == HttpStatusCode.NotFound) {
                    null
                } else {
                    it.body()
                }
            }
}
