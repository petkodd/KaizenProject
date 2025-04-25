package com.example.kaizenproject.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface ScreenState
interface Action

abstract class BaseViewModel<S : ScreenState, A : Action> : ViewModel() {

    private val initialState: S by lazy { createInitialScreenState() }
    protected abstract fun createInitialScreenState(): S

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    private val stateMutex = Mutex()
    val state = _state.asStateFlow()

    private val _actions: MutableSharedFlow<A> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            _actions.collect {
                handleActions(it)
            }
        }
    }

    protected abstract suspend fun handleActions(action: A)

    protected suspend fun updateScreenState(reduce: S.() -> S) {
        stateMutex.withLock {
            _state.value = state.value.reduce()
        }
    }

    fun sendAction(action: A) = viewModelScope.launch { _actions.emit(action) }
}
