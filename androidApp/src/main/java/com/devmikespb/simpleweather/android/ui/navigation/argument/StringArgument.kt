package com.devmikespb.simpleweather.android.ui.navigation.argument

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import java.net.URLEncoder

object StringArgument : Argument<String> {
    private val ENCODING: String = "utf-8"

    override val name: String = "stringArg"

    override val navArgumentBuilder: NavArgumentBuilder.() -> Unit =
        {
            type = NavType.StringType
            nullable = true
        }

    override fun restoreFromSavedState(savedStateHandle: SavedStateHandle): String =
        // String value in a savedStateHandle come already urldecoded.
        checkNotNull(savedStateHandle.get<String>(name))

    override fun restoreFromBundle(bundle: Bundle?): String =
        // String value in a bundle come already urldecoded.
        checkNotNull(checkNotNull(bundle).getString(name))

    override fun convertToUrlSafeString(value: String): String =
        URLEncoder.encode(value, ENCODING)
}
