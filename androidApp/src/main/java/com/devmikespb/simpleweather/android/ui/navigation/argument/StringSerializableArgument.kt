package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import com.devmikespb.simpleweather.android.ui.navigation.StringSerializer

class StringSerializableArgument<T>(private val stringSerializer: StringSerializer<T>) : Argument<T> {

    override val name: String = "stringSerializableArg"

    override val navArgumentBuilder: NavArgumentBuilder.() -> Unit =
        {
            type = NavType.StringType
            nullable = true
        }

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): T =
        // String value in a savedStateHandle come already urldecoded.
        stringSerializer.fromString(checkNotNull(savedStateHandle.get<String>(name)))

    override fun restoreFromBundle(bundle: Bundle?): T =
        // String value in a bundle come already urldecoded.
        stringSerializer.fromString(checkNotNull(checkNotNull(bundle).getString(name)))

    override fun convertToUrlSafeString(value: T): String =
        Uri.encode(stringSerializer.toString(value))

    override fun wrapToBundle(value: T): Bundle =
        bundleOf(name to stringSerializer.toString(value))
}
