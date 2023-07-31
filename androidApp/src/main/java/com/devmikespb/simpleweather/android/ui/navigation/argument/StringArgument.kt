package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import java.net.URLDecoder
import java.net.URLEncoder

object StringArgument : Argument<String> {
    private val ENCODING: String = "utf-8"

    override val name: String = "stringArg"

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): String =
        URLDecoder.decode(checkNotNull(savedStateHandle.get<String>(name)), ENCODING)

    override fun restoreFromBundle(bundle: Bundle): String =
        URLDecoder.decode(checkNotNull(bundle.getString(name)), ENCODING)

    override fun convertToUrlSafeString(value: String): String =
        URLEncoder.encode(value, ENCODING)
}
