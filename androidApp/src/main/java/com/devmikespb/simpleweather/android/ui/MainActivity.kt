package com.devmikespb.simpleweather.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.devmikespb.simpleweather.android.ui.navigation.IntDestination
import com.devmikespb.simpleweather.android.ui.navigation.NavHost
import com.devmikespb.simpleweather.android.ui.navigation.StringSerializableDestination
import com.devmikespb.simpleweather.android.ui.navigation.UnitDestination
import com.devmikespb.simpleweather.android.ui.navigation.addDestination
import com.devmikespb.simpleweather.android.ui.navigation.argument.IntArgument
import com.devmikespb.simpleweather.android.ui.navigation.navigate
import com.devmikespb.simpleweather.android.ui.screen.details.PlaceDetailsScreen
import com.devmikespb.simpleweather.android.ui.screen.details.PlaceDetailsScreenArguments
import com.devmikespb.simpleweather.android.ui.screen.main.WeatherScreen
import com.devmikespb.simpleweather.android.ui.theme.SimpleWeatherTheme
import com.devmikespb.simpleweather.presentation.details.PlaceDetailsStore
import com.devmikespb.simpleweather.presentation.main.WeatherStore
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleWeatherTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = MainFlow.SearchDestination) {
                    addDestination(MainFlow.SearchDestination) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            WeatherScreen(
                                viewModel = koinViewModel(),
                                onExecuteCommand = { command ->
                                    when (command) {
                                        is WeatherStore.Command.DetailsScreen -> {
                                            navController.navigate(
                                                MainFlow.PlaceDetailsDestination,
                                                command.let {
                                                    PlaceDetailsScreenArguments(
                                                        place = it.place,
                                                        weather = it.placeWeather,
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                    addDestination(MainFlow.PlaceDetailsDestination) {
                        PlaceDetailsScreen(
                            viewModel = koinViewModel(),
                        ) { command ->
                            when (command) {
                                PlaceDetailsStore.Command.Exit -> {
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                    addDestination(MainFlow.Test1Destination) {
                        val param = IntArgument.restoreFromBundle(it.arguments!!)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.background),
                        ) {
                            Text(
                                text = "Test $param",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable {
                                        navController
                                            .popBackStack()
                                    }
                            )
                        }
                    }
                    addDestination(MainFlow.Test2Destination) {
                        val param = IntArgument.restoreFromBundle(it.arguments!!)
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.background),
                        ) {
                            Text(
                                text = "Test $param",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .clickable {
                                        navController
                                            .popBackStack()
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

private object MainFlow {
    object SearchDestination : UnitDestination("Search")
    object PlaceDetailsDestination : StringSerializableDestination<PlaceDetailsScreenArguments>(
        "PlaceDetails",
        PlaceDetailsScreenArguments.Companion
    )

    object Test1Destination : IntDestination("Test1")
    object Test2Destination : IntDestination("Test2")
}

