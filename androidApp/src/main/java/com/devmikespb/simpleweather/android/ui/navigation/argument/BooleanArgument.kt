package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType

object BooleanArgument : Argument<Boolean> {
    override val name: String = "boolArg"

    override val navArgumentBuilder: NavArgumentBuilder.() -> Unit =
        {
            type = NavType.BoolType
        }

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): Boolean =
        checkNotNull(savedStateHandle.get<Boolean>(name))

    override fun restoreFromBundle(bundle: Bundle?): Boolean =
        checkNotNull(bundle).getBoolean(name)

    override fun convertToUrlSafeString(value: Boolean): String =
        value.toString()
}
