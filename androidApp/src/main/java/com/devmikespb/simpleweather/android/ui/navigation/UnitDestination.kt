package com.devmikespb.simpleweather.android.ui.navigation

import androidx.navigation.NamedNavArgument

open class UnitDestination(name: String) : TypedDestination<Unit> {
    override val route: String = name

    override val arguments: List<NamedNavArgument> = emptyList()

    override fun getNavigationString(argument: Unit): String = route
}
