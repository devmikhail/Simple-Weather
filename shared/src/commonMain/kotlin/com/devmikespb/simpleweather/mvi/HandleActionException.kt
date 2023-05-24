package com.devmikespb.simpleweather.mvi

class HandleActionException(
    private val action: Any,
    private val throwable: Throwable,
): Exception(throwable) {
    override fun toString(): String = "HandleActionException(action: $action, throwable: $throwable)"
}
