package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgumentBuilder

interface Argument<T> {
    val name: String
    val navArgumentBuilder: (NavArgumentBuilder.() -> Unit)
    fun restoreFromSavedState(savedStateHandle: SavedStateHandle): T
    fun restoreFromBundle(bundle: Bundle?): T
    fun convertToUrlSafeString(value: T): String
    fun wrapToBundle(value: T): Bundle =
        bundleOf(name to value)
}
