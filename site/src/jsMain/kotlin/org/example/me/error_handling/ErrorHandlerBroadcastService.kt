package org.example.me.error_handling

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IErrorHandlerBroadcastService {
    val messageResourceIdWrapper: StateFlow<MessageResourceIdWrapper?>
    fun setErrorMessage(message: String?, errorAction: ErrorAction? = null)
    fun processNextMessage()
}

object ErrorHandlerBroadcastService : IErrorHandlerBroadcastService {
    private val queue = ArrayDeque<MessageResourceIdWrapper>()
    private val _messageResourceIdWrapper = MutableStateFlow<MessageResourceIdWrapper?>(null)
    override val messageResourceIdWrapper: StateFlow<MessageResourceIdWrapper?>
        get() = _messageResourceIdWrapper

    override fun setErrorMessage(message: String?, errorAction: ErrorAction?) {
        if (message == null && errorAction == null) return
        queue.addLast(
            MessageResourceIdWrapper(
                message = message,
                errorAction = errorAction
            )
        )
    }

    override fun processNextMessage() {
        if (queue.isNotEmpty()) {
            val peekValue = queue.first()
            _messageResourceIdWrapper.value = peekValue
            queue.removeFirst()
        } else {
            _messageResourceIdWrapper.value = null
        }
    }
}

// An instance class used to achieve reference comparison of the MutableStateFlow setter new value
class MessageResourceIdWrapper(
    val message: String? = null,
    val errorAction: ErrorAction? = null
)

enum class ErrorAction {
    LOG_OUT;
}