package com.devmikespb.simpleweather.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

fun <T, A> Flow<T>.mapToAction(block: (Result<T>) -> A): Flow<A> =
    this.map { t -> block(Result.success(t)) }
        .catch { t -> emit(block(Result.failure(t))) }

fun <T, A> executeAsEffect(block: (Result<T>) -> A, function: suspend () -> T): Flow<A> =
    flow { emit(function()) }
        .mapToAction(block)
