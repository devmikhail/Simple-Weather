package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import com.devmikespb.simpleweather.android.ui.navigation.StringSerializer
import java.net.URLDecoder
import java.net.URLEncoder

class StringSerializableArgument<T>(private val stringSerializer: StringSerializer<T>) : Argument<T> {
    private val ENCODING: String = "utf-8"

    override val name: String = "stringSerializableArg"

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): T =
        stringSerializer.fromString(URLDecoder.decode(checkNotNull(savedStateHandle.get<String>(name)), ENCODING))

    override fun restoreFromBundle(bundle: Bundle): T =
        stringSerializer.fromString(URLDecoder.decode(checkNotNull(bundle.getString(name)), ENCODING))

    override fun convertToUrlSafeString(value: T): String =
        URLEncoder.encode(stringSerializer.toString(value), ENCODING)
}
