package org.example.me.view_models

import kotlinx.coroutines.delay
import org.example.me.repositories.ResponseState
import org.example.me.repositories.ResponseState.ActiveResponseState.*
import org.example.me.repositories.ResponseState.Idle
import org.example.me.error_handling.ErrorAction
import org.example.me.error_handling.ErrorHandler
import org.example.me.models.domain.UserModel
import org.example.me.use_cases.IGetUsersUseCase

class CustomBackendDemoViewModel(
    private val getUsersUseCase: IGetUsersUseCase,
    private val errorHandler: ErrorHandler
): BaseViewModel<CustomBackendDemoViewModel.Model, CustomBackendDemoViewModel.Event>(
    model = Model(
        isLoading = true,
        userModelsResponseState = Idle
    )
) {
    init {
        console.log("CustomBackendDemoViewModel init = ${this.hashCode()} and random=${(0..100).random()}")
    }

    data class Model(
        val isLoading: Boolean,
        val userModelsResponseState: ResponseState<List<UserModel>>
    )

    sealed interface Event {
        data object GetUsers: Event
    }

    override suspend fun processEvent(event: Event) {
        when (event) {
            is Event.GetUsers -> {
                getUsers()
            }
        }
    }

    private suspend fun getUsers() {
        delay(1000)
        getUsersUseCase
            .getUsers()
            .collect { state ->
                when (state) {
                    is Loading -> {
                        updateModelState { model ->
                            model.copy(
                                isLoading = true,
                                userModelsResponseState = state
                            )
                        }
                    }
                    is Failure -> {
                        updateModelState { model ->
                            model.copy(
                                isLoading = false,
                                userModelsResponseState = state
                            )
                        }
//                        //Default error handling
//                        errorHandler.handleError(state.throwable)

                        //Custom Error Handler
                        errorHandler.apply {
                            defaultErrorHandler(
                                onApiException = defaultApiExceptionHandler(
                                    on4xx = {
                                        broadcastService.setErrorMessage(
                                            message = "404 resource not found!",
                                            errorAction = ErrorAction.LOG_OUT
                                        )
                                    }

                                )
                            ).invoke(state.throwable)
                        }
                    }
                    is Success -> {
                        updateModelState { model ->
                            model.copy(
                                isLoading = false,
                                userModelsResponseState = state
                            )
                        }
                    }
                }
            }
    }

    companion object {
        const val TAG = "CustomBackendDemoViewModel"
    }
}