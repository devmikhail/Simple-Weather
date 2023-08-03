package com.devmikespb.simpleweather.android.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.devmikespb.simpleweather.android.ui.navigation.argument.BooleanArgument

open class BooleanDestination(name: String) : TypedDestination<Boolean> {
    private val typedArgument = BooleanArgument

    private val argName: String = typedArgument.name

    override val route: String = "$name?$argName={$argName}"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) {
            type = NavType.BoolType
        }
    )

    override fun getNavigationString(argument: Boolean): String =
        route.replace("{$argName}", argument.toString())
}
