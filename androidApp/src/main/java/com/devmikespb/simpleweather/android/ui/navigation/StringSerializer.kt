package com.devmikespb.simpleweather.android.ui.navigation

interface StringSerializer<T> {
    fun toString(t: T): String
    fun fromString(string: String): T
}
