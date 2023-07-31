package com.devmikespb.simpleweather.android.ui.navigation

import androidx.navigation.NamedNavArgument

interface TypedDestination<T> {
    val route: String
    val arguments: List<NamedNavArgument>
    fun getNavigationString(argument: T): String
}
