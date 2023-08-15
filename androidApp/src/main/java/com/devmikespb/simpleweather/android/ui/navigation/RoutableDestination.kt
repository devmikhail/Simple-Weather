package com.devmikespb.simpleweather.android.ui.navigation

import androidx.navigation.NamedNavArgument

interface RoutableDestination {
    val route: String
    val arguments: List<NamedNavArgument>
}
