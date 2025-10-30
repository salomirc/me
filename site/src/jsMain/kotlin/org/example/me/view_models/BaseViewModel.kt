package org.example.me.view_models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface MVI<Model, Event> {
    val modelStateFlow: StateFlow<Model>
    suspend fun processEvent(event: Event)
}

abstract class BaseViewModel<Model, Event>(model: Model): MVI<Model, Event> {
    private val _modelStateFlow: MutableStateFlow<Model> = MutableStateFlow(model)
    override val modelStateFlow: StateFlow<Model> = _modelStateFlow

    fun updateModelState(function: (Model) -> Model) {
        _modelStateFlow.update(function)
    }

    suspend fun updateModelStateSuspend(function: suspend (Model) -> Model) {
        _modelStateFlow.update { model ->
            function(model)
        }
    }
}
