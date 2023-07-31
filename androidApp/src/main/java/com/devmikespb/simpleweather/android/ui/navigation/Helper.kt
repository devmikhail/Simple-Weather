package com.devmikespb.simpleweather.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavigatorProvider
import androidx.navigation.PopUpToBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.get

fun <T> NavHostController.navigate(
    destination: TypedDestination<T>,
    arguments: T,
    navOptions: NavOptions? = null,
) {
    navigate(
        route = destination.getNavigationString(arguments),
        navOptions = navOptions,
    )
}

fun NavHostController.navigate(
    unitDestination: UnitDestination,
    navOptions: NavOptions? = null,
) {
    navigate(
        route = unitDestination.getNavigationString(Unit),
        navOptions = navOptions,
    )
}

fun <T> TypedDestination<T>.toNavDestination(
    navigatorProvider: NavigatorProvider,
    content: @Composable (NavBackStackEntry) -> Unit,
): NavDestination =
    ComposeNavigator
        .Destination(
            navigator = navigatorProvider[ComposeNavigator::class],
            content = content,
        )
        .apply {
            this.route = this@toNavDestination.route
            this@toNavDestination.arguments.forEach { (argumentName, argument) ->
                addArgument(argumentName, argument)
            }
        }

fun NavGraphBuilder.addDestination(
    typedDestination: TypedDestination<*>,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    addDestination(
        typedDestination.toNavDestination(provider, content)
    )
}

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: TypedDestination<*>,
    builder: NavGraphBuilder.() -> Unit,
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination.route,
        builder = builder
    )
}

fun NavOptionsBuilder.popUpTo(
    typedDestination: TypedDestination<*>,
    popUpToBuilder: PopUpToBuilder.() -> Unit = {},
) {
    popUpTo(route = typedDestination.route, popUpToBuilder = popUpToBuilder)
}
