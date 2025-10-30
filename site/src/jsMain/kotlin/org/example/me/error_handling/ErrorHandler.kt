package org.example.me.error_handling

import org.example.me.api_caller.ApiException

interface ILogger {
    fun log(tag: String, message: String)
    fun error(tag: String, message: String, throwable: Throwable? = null)
}

object WebLogger : ILogger {
    override fun log(tag: String, message: String) {
        console.log(tag, message)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        console.error(tag, message, throwable)
    }
}

open class ErrorHandler(
    private var viewModelName: String,
    private var logger: ILogger = WebLogger,
    val broadcastService: IErrorHandlerBroadcastService = ErrorHandlerBroadcastService
) {

    fun handleError(t: Throwable) {
        defaultErrorHandler().invoke(t)
    }

    open fun defaultErrorHandler(
        onApiException: (ApiException) -> Unit = defaultApiExceptionHandler(),
        onOtherException: (Throwable) -> Unit = defaultOtherExceptionHandler()
    ): (Throwable) -> Unit = { throwable ->

        logger.error(ERROR_HANDLER_TAG, "$viewModelName exception: ", throwable)

        // Order is very important! From specific to general.
        when (throwable) {
            is ApiException -> onApiException(throwable)
            else -> onOtherException(throwable)
        }
    }

    private fun defaultOtherExceptionHandler(): (Throwable) -> Unit = {
        setDefaultMessage()
    }

    open fun defaultApiExceptionHandler(
        on401: (ApiException) -> Unit = defaultHttp401Handler(),
        on403Forbidden: (ApiException) -> Unit = defaultHttp403ForbiddenHandler(),
        on409: (ApiException) -> Unit = defaultHttp4xxHandler(),
        on4xx: (ApiException) -> Unit = defaultHttp4xxHandler(),
        on5xx: (ApiException) -> Unit = defaultHttp5xxHandler(),
    ): (ApiException) -> Unit = { apiException ->

        when (apiException.code) {
            401 -> on401(apiException)
            403 -> on403Forbidden(apiException)
            409 -> on409(apiException)
            400, 402, in (404..499) -> on4xx(apiException)
            else -> on5xx(apiException)
        }
    }

    private fun defaultHttp401Handler(): (ApiException) -> Unit = {
        broadcastService.setErrorMessage(
            message = "An authentication error occurred. Please try again.",
            errorAction = ErrorAction.LOG_OUT
        )
    }

    private fun defaultHttp403ForbiddenHandler(): (ApiException) -> Unit = {
        setDefaultMessage()
    }

    private fun defaultHttp4xxHandler(): (ApiException) -> Unit = {
        setDefaultMessage()
    }

    private fun defaultHttp5xxHandler(): (ApiException) -> Unit = {
        setDefaultMessage()
    }

    private fun setDefaultMessage() {
        broadcastService.setErrorMessage("Something went wrong!")
    }

    companion object {
        const val ERROR_HANDLER_TAG = "ErrorHandlerLog"
    }

}