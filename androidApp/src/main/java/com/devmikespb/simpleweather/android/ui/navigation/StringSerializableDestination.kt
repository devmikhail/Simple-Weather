package com.devmikespb.simpleweather.android.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.devmikespb.simpleweather.android.ui.navigation.argument.StringSerializableArgument

open class StringSerializableDestination<T>(
    name: String,
    serializer: StringSerializer<T>,
) : TypedDestination<T> {
    private val typedArgument = StringSerializableArgument(serializer)

    private val argName: String = typedArgument.name

    private val ENCODING: String = "utf-8"

    override val route: String = "$name?$argName={$argName}"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) {
            type = NavType.StringType
            nullable = true
        }
    )

    override fun getNavigationString(argument: T): String =
        route.replace("{$argName}", typedArgument.convertToUrlSafeString(argument))
}
