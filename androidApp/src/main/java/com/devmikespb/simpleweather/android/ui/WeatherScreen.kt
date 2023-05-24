package com.devmikespb.simpleweather.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.devmikespb.simpleweather.android.ui.theme.SimpleWeatherTheme
import com.devmikespb.simpleweather.android.presentation.WeatherViewModel
import com.devmikespb.simpleweather.mvi.ActionDispatcher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.devmikespb.simpleweather.android.R
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.domain.entity.TemperatureUnit
import com.devmikespb.simpleweather.presentation.WeatherStore.Action
import com.devmikespb.simpleweather.presentation.WeatherStore.State
import kotlinx.coroutines.delay

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val state by viewModel.stateFlow.collectAsState()
    WeatherScreenContent(state = state, actionDispatcher = viewModel)
}

@Composable
fun WeatherScreenContent(state: State, actionDispatcher: ActionDispatcher<Action>) {
    Scaffold { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            var city by remember { mutableStateOf("") }

            Text(text = stringResource(id = R.string.main_title))
            TextField(
                value = city,
                onValueChange = {
                    city = it
                },
                label = { Text(stringResource(id = R.string.main_city)) }
            )
            LaunchedEffect(key1 = city) {
                if (city.isBlank()) return@LaunchedEffect

                delay(1000)
                actionDispatcher.dispatch(Action.UpdateCityName(city))
            }
            if(state.isCityWeatherLoading) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            } else {
                Text(
                    text = stringResource(
                        id = R.string.main_temperature,
                        formatTemperature(state.cityWeather?.temperature),
                        formatTemperatureUnit(state.cityWeather?.temperatureUnit)
                    )
                )
            }
        }
    }
}

private class WeatherScreenPreviewParameterProvider: PreviewParameterProvider<State> {
    override val values: Sequence<State> =
        sequenceOf(
            State(),
            State(isCityWeatherLoading = true),
            State(
                cityName = "Saint-Petersburg",
                cityWeather = PlaceWeather(temperature = 25.0, temperatureUnit = TemperatureUnit.Celsius)
            )
        )
}

@Preview
@Composable
private fun WeatherScreenContentPreview(
    @PreviewParameter(WeatherScreenPreviewParameterProvider::class) state: State
) {
    SimpleWeatherTheme {
        WeatherScreenContent(
            state = state,
            actionDispatcher = StubDispatcher()
        )
    }
}
