package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle

object BooleanArgument : Argument<Boolean> {
    override val name: String = "boolArg"

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): Boolean =
        checkNotNull(savedStateHandle.get<Boolean>(name))

    override fun restoreFromBundle(bundle: Bundle): Boolean =
        bundle.getBoolean(name)

    override fun convertToUrlSafeString(value: Boolean): String =
        value.toString()

    override fun wrapToBundle(value: Boolean): Bundle =
        bundleOf(name to value)
}
