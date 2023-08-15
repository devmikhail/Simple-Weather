package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType

object IntArgument : Argument<Int> {
    override val name: String = "intArg"

    override val navArgumentBuilder: NavArgumentBuilder.() -> Unit =
        {
            type = NavType.IntType
        }

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): Int =
        checkNotNull(savedStateHandle.get<Int>(name))

    override fun restoreFromBundle(bundle: Bundle?): Int =
        checkNotNull(bundle).getInt(name)

    override fun convertToUrlSafeString(value: Int): String =
        value.toString()
}
