package com.devmikespb.simpleweather.android.ui.screen.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devmikespb.simpleweather.android.R
import com.devmikespb.simpleweather.android.presentation.PlaceDetailsViewModel
import com.devmikespb.simpleweather.android.ui.StubDispatcher
import com.devmikespb.simpleweather.android.ui.formatTemperature
import com.devmikespb.simpleweather.android.ui.formatTemperatureUnit
import com.devmikespb.simpleweather.android.ui.theme.SimpleWeatherTheme
import com.devmikespb.simpleweather.domain.entity.Place
import com.devmikespb.simpleweather.domain.entity.PlaceWeather
import com.devmikespb.simpleweather.domain.entity.TemperatureUnit
import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.Action
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.Command
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore.State
import kotlinx.coroutines.launch

@Composable
fun PlaceDetailsScreen(viewModel: PlaceDetailsViewModel, onExecuteCommand: (Command) -> Unit) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    state.commandToExecute?.let { command ->
        LaunchedEffect(command) {
            onExecuteCommand(command)
            viewModel.dispatch(Action.CommandWasExecuted(command))
        }
    }
    LocationDetailsScreenContent(state = state, actionDispatcher = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreenContent(state: State, actionDispatcher: ActionDispatcher<Action>) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.place.name)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                actionDispatcher.dispatch(Action.ExecuteCommand(Command.Exit))
                            }
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(text = state.place.name)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = R.string.main_temperature,
                    formatTemperature(state.placeWeather.temperature),
                    formatTemperatureUnit(state.placeWeather.temperatureUnit)
                )
            )
        }
    }
}

private class LocationDetailsScreenParameterProvider : PreviewParameterProvider<State> {
    override val values: Sequence<State> =
        sequenceOf(
            State(
                place = Place("London"),
                placeWeather = PlaceWeather(temperature = 15.0, temperatureUnit = TemperatureUnit.Celsius)
            )
        )
}

@Preview
@Composable
private fun WeatherScreenContentPreview(
    @PreviewParameter(LocationDetailsScreenParameterProvider::class) state: State,
) {
    SimpleWeatherTheme {
        LocationDetailsScreenContent(
            state = state,
            actionDispatcher = StubDispatcher()
        )
    }
}
