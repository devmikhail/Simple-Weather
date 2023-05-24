package com.devmike.core.mvi

import co.touchlab.kermit.Logger
import com.devmikespb.simpleweather.mvi.ActionDispatcher
import com.devmikespb.simpleweather.mvi.HandleActionException
import com.devmikespb.simpleweather.mvi.Reducer
import com.devmikespb.simpleweather.mvi.Store
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
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

class StoreImpl<ACTION : Any, STATE> constructor(
    initialState: STATE,
    coroutineScope: CoroutineScope,
    private val reducer: Reducer<STATE>,
    initialActions: List<ACTION> = emptyList(),
) : Store<STATE>,
    ActionDispatcher<ACTION> {

    private val mutableStateFlow: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    private val actionFlow: MutableSharedFlow<Any> = MutableSharedFlow()
    override val stateFlow: StateFlow<STATE> = mutableStateFlow.asStateFlow()

    override suspend fun dispatch(action: ACTION) {
        // Suspend action dispatch until action collection is launched
        actionFlow.subscriptionCount.first { it > 0 }
        actionFlow.emit(action)
    }

    init {
        actionFlow
            .onEach { action ->
                Logger.d { "Got action: $action" }
                try {
                    reducer.reduce(
                        action = action,
                        state = stateFlow.value,
                        updateState = {
                            mutableStateFlow.update { state ->
                                state.it()
                            }
                        },
                    )
                } catch (th: Throwable) {
                    Logger.w { "Caught throwable while handling action: $action, throwable: $th" }
                    coroutineScope.launch {
                        actionFlow.emit(
                            HandleActionException(
                                action = action,
                                throwable = th,
                            )
                        )
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
