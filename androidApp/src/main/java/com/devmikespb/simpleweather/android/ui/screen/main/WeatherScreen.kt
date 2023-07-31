package com.devmikespb.simpleweather.android.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devmikespb.simpleweather.android.R
import com.devmikespb.simpleweather.android.presentation.WeatherViewModel
import com.devmikespb.simpleweather.android.ui.StubDispatcher
import com.devmikespb.simpleweather.android.ui.formatTemperature
import com.devmikespb.simpleweather.android.ui.formatTemperatureUnit
import com.devmikespb.simpleweather.android.ui.theme.SimpleWeatherTheme
import com.devmikespb.simpleweather.domain.entity.Place
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.domain.entity.TemperatureUnit
import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.presentation.main.WeatherStore
import com.devmikespb.simpleweather.presentation.main.WeatherStore.Action
import com.devmikespb.simpleweather.presentation.main.WeatherStore.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(viewModel: WeatherViewModel, onExecuteCommand: (WeatherStore.Command) -> Unit) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    state.commandToExecute?.let { command ->
        LaunchedEffect(command) {
            onExecuteCommand(command)
            viewModel.dispatch(Action.CommandWasExecuted(command))
        }
    }
    WeatherScreenContent(state = state, actionDispatcher = viewModel)
}

@Composable
fun WeatherScreenContent(state: State, actionDispatcher: ActionDispatcher<Action>) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            var city by remember { mutableStateOf(state.cityName ?: "") }

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
            if (state.isCityWeatherLoading) {
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
            if (state.cityWeather != null && state.cityName != null) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val cityName = state.cityName
                            val cityWeather = state.cityWeather
                            if (cityName != null && cityWeather != null) {
                                actionDispatcher.dispatch(
                                    Action.ExecuteCommand(
                                        WeatherStore.Command.DetailsScreen(
                                            place = Place(cityName),
                                            placeWeather = cityWeather
                                        )
                                    )
                                )
                            }
                        }
                    }
                ) {
                    Text(text = "Details")
                }
            }
        }
    }
}

private class WeatherScreenPreviewParameterProvider : PreviewParameterProvider<State> {
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
    @PreviewParameter(WeatherScreenPreviewParameterProvider::class) state: State,
) {
    SimpleWeatherTheme {
        WeatherScreenContent(
            state = state,
            actionDispatcher = StubDispatcher()
        )
    }
}
