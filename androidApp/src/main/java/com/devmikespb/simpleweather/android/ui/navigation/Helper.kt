package com.devmikespb.simpleweather.android.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavigatorProvider
import androidx.navigation.PopUpToBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.get
import androidx.navigation.navOptions
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.toExtras
import org.koin.core.annotation.KoinInternalApi

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
        route = unitDestination.route,
        navOptions = navOptions,
    )
}

fun NavController.optionsReplaceCurrent(): NavOptions = navOptions {
    this@optionsReplaceCurrent.currentDestination?.route?.let {
        popUpTo(it) { inclusive = true }
    }
}

fun RoutableDestination.toNavDestination(
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
    typedDestination: RoutableDestination,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    addDestination(
        typedDestination.toNavDestination(provider, content)
    )
}

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: RoutableDestination,
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

fun NavController.findCurrentDestination(list: List<RoutableDestination>): RoutableDestination? =
    currentDestination?.route?.let { currentRoute ->
        list.forEach {
            if (it.route == currentRoute) {
                return@let it
            }
        }
        return@let null
    }

// This is needed to inject arguments to a NavHost start destination.
@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified T : ViewModel> koinViewModel(
    args: Bundle,
): T =
    koinViewModel(
        extras = checkNotNull(
            args.toExtras(
                checkNotNull(LocalViewModelStoreOwner.current)
            )
        )
    )
