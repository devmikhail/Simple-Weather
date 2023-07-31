package com.devmikespb.simpleweather.android.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.devmikespb.simpleweather.android.ui.navigation.argument.IntArgument

open class IntDestination(name: String) : TypedDestination<Int> {
    private val typedArgument = IntArgument

    private val argName: String = typedArgument.name

    override val route: String = "$name?$argName={$argName}"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) {
            type = NavType.IntType
        }
    )

    override fun getNavigationString(argument: Int): String =
        route.replace("{$argName}", argument.toString())
}
