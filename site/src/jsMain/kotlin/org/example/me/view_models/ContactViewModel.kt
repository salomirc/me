package org.example.me.view_models

import org.example.me.error_handling.ErrorHandler
import org.example.me.models.domain.SendMailRequestModel
import org.example.me.models.domain.SendMailResponseModel
import org.example.me.repositories.ISendMailRepository
import org.example.me.repositories.ResponseState
import org.example.me.repositories.ResponseState.ActiveResponseState.*
import org.example.me.repositories.ResponseState.Idle

class ContactViewModel(
    private val sendMailRepository: ISendMailRepository,
    private val errorHandler: ErrorHandler
): BaseViewModel<ContactViewModel.Model, ContactViewModel.Event>(
    model = Model(
        isLoading = true,
        sendMailResponseState = Idle
    )
) {
    init {
        console.log("ContactViewModel init = ${this.hashCode()} and random=${(0..100).random()}")
    }

    data class Model(
        val isLoading: Boolean,
        val sendMailResponseState: ResponseState<SendMailResponseModel>
    )

    sealed interface Event {
        data class SendEmail(
            val name: String,
            val email: String,
            val message: String
        ): Event
    }

    override suspend fun processEvent(event: Event) {
        when (event) {
            is Event.SendEmail -> {
                sendEmailRequest(
                    SendMailRequestModel(
                        name = event.name,
                        email = event.email,
                        message = event.message
                    )
                )
            }
        }
    }

    private suspend fun sendEmailRequest(sendMailRequestModel: SendMailRequestModel) {
        sendMailRepository
            .sendMail(sendMailRequestModel)
            .collect { state ->
                when (state) {
                    is Loading -> {
                        updateModelState { model ->
                            model.copy(
                                isLoading = true,
                                sendMailResponseState = state
                            )
                        }
                    }
                    is Failure -> {
                        updateModelState { model ->
                            model.copy(
                                isLoading = false,
                                sendMailResponseState = state
                            )
                        }
                        //Default error handling
                        errorHandler.handleError(state.throwable)
                    }
                    is Success -> {
                        updateModelState { model ->
                            model.copy(
                                isLoading = false,
                                sendMailResponseState = state
                            )
                        }
                    }
                }
            }
    }

    companion object {
        const val TAG = "ContactViewModel"
    }
}