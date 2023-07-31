package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle

object IntArgument : Argument<Int> {
    override val name: String = "intArg"

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): Int =
        checkNotNull(savedStateHandle.get<Int>(name))

    override fun restoreFromBundle(bundle: Bundle): Int =
        bundle.getInt(name)

    override fun convertToUrlSafeString(value: Int): String =
        value.toString()
}
