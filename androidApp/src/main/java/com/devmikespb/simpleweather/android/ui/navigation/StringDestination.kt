package com.devmikespb.simpleweather.android.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.devmikespb.simpleweather.android.ui.navigation.argument.StringArgument

open class StringDestination(name: String) : TypedDestination<String> {
    private val typedArgument = StringArgument

    private val argName = typedArgument.name

    override val route: String = "$name?$argName={$argName}"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) {
            type = NavType.StringType
            nullable = true
        }
    )

    override fun getNavigationString(argument: String): String =
        route.replace("{$argName}", typedArgument.convertToUrlSafeString(argument))
}
