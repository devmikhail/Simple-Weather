package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import com.devmikespb.simpleweather.android.ui.navigation.StringSerializer
import java.net.URLEncoder

class StringSerializableArgument<T>(private val stringSerializer: StringSerializer<T>) : Argument<T> {
    private val ENCODING: String = "utf-8"

    override val name: String = "stringSerializableArg"

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): T =
        // String value in a savedStateHandle come already urldecoded.
        stringSerializer.fromString(checkNotNull(savedStateHandle.get<String>(name)))

    override fun restoreFromBundle(bundle: Bundle): T =
        // String value in a bundle come already urldecoded.
        stringSerializer.fromString(checkNotNull(bundle.getString(name)))

    override fun convertToUrlSafeString(value: T): String =
        URLEncoder.encode(stringSerializer.toString(value), ENCODING)

    override fun wrapToBundle(value: T): Bundle =
        bundleOf(name to stringSerializer.toString(value))
}
