package com.devmikespb.simpleweather.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ElmLikeStoreImpl<ACTION, STATE>(
    initialState: STATE,
    initialActions: List<ACTION>,
    private val coroutineScope: CoroutineScope,
    private val updater: Updater<ACTION, STATE>,
) : Store<ACTION, STATE> {
    private val mutableStateFlow: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    private val actionFlow: MutableSharedFlow<ACTION> = MutableSharedFlow()
    override val stateFlow: StateFlow<STATE> = mutableStateFlow.asStateFlow()

    override suspend fun dispatch(action: ACTION) {
        // Suspend action dispatch until action collection is launched
        actionFlow.subscriptionCount.first { it > 0 }
        actionFlow.emit(action)
    }

    init {
        actionFlow
            .onEach { action ->
                var effect: Flow<ACTION>? = null
                try {
                    mutableStateFlow.update { state ->
                        val result = updater.update(action = action, state = state)
                        effect = result.sideEffect
                        result.state ?: state
                    }
                } catch (th: Throwable) {
                    mutableStateFlow.update { state ->
                        updater.onUnhandledError(th, state)
                    }
                }

                effect?.let { flow ->
                    coroutineScope.launch {
                        flow.collect(actionFlow)
                    }
                }
            }
            .launchIn(coroutineScope)

        initialActions.forEach { action ->
            coroutineScope.launch {
                actionFlow.emit(action)
            }
        }
    }
}
