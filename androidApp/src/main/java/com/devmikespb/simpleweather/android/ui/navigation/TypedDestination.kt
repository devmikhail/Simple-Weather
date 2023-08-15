package com.devmikespb.simpleweather.android.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument
import com.devmikespb.simpleweather.android.ui.navigation.argument.Argument

open class TypedDestination<T>(
    name: String,
    private val typedArgument: Argument<T>,
) : RoutableDestination {

    private val argName: String = typedArgument.name

    override val route: String = "$name?$argName={$argName}"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName, typedArgument.navArgumentBuilder)
    )

    fun getNavigationString(argument: T): String =
        route.replace("{$argName}", typedArgument.convertToUrlSafeString(argument))
}
