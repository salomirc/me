package org.example.me.api_caller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.fetch.Response


interface IWebApiCaller {

    suspend fun invoke(
        callBlock: suspend () -> Response
    ): Result<String>

    suspend fun invokeUnit(
        callBlock: suspend () -> Response
    ): Result<Unit>

    suspend fun invokeNullable(
        callBlock: suspend () -> Response
    ): Result<String?>

    suspend fun raw(
        callBlock: suspend () -> Response
    ): Result<ApiSuccess<String>>

    suspend fun rawUnit(
        callBlock: suspend () -> Response
    ): Result<ApiSuccess<Unit>>

    suspend fun rawNullable(
        callBlock: suspend () -> Response
    ): Result<ApiSuccess<String?>>
}


class WebApiCaller() : IWebApiCaller {
    override suspend fun invoke(callBlock: suspend () -> Response): Result<String> =
        raw(callBlock).mapCatching { it.body }

    override suspend fun invokeUnit(callBlock: suspend () -> Response): Result<Unit> =
        rawUnit(callBlock).mapCatching { it.body }

    override suspend fun invokeNullable(callBlock: suspend () -> Response): Result<String?> =
        rawNullable(callBlock).mapCatching { it.body }

    override suspend fun raw(callBlock: suspend () -> Response): Result<ApiSuccess<String>>{
        return networkCallHandling(
            callBlock = callBlock,
            handleResponseBody = { bytes ->
                if (bytes.isEmpty()) {
                    throw EmptyByteArrayResponseBodyException(NOT_NULL_BODY_EXPECTED_MESSAGE)
                } else {
                    bytes.decodeToString()
                }
            }
        )
    }

    override suspend fun rawUnit(callBlock: suspend () -> Response): Result<ApiSuccess<Unit>> {
        return networkCallHandling(
            callBlock = callBlock,
            handleResponseBody = { bytes ->
                if (bytes.isEmpty()) Unit else {
                    throw NonEmptyByteArrayResponseBodyException(NULL_BODY_EXPECTED_MESSAGE)
                }
            }
        )
    }

    override suspend fun rawNullable(callBlock: suspend () -> Response): Result<ApiSuccess<String?>> {
        return networkCallHandling(
            callBlock = callBlock,
            handleResponseBody = { bytes ->
                if (bytes.isEmpty()) {
                    null
                } else {
                    bytes.decodeToString()
                }
            }
        )
    }

    private suspend fun <T> networkCallHandling(
        callBlock: suspend () -> Response,
        handleResponseBody: (data: ByteArray) -> T
    ): Result<ApiSuccess<T>> {
        return runCatching {
            val response =  withContext(Dispatchers.Default) {
                callBlock()
            }
            if (response.ok) {
                ApiSuccess(
                    code = response.status.toInt(),
                    message = response.statusText,
                    headers = response.headers,
                    body = handleResponseBody(response.localGetBodyBytes()),
                    rawResponse = response
                )
            } else {
                throw ApiException(
                    code = response.status.toInt(),
                    errorBodyString = response.localGetBodyBytes().decodeToString(),
                    response = response,
                    bodyBytes = response.localGetBodyBytes()
                )
            }
        }.onFailure { t ->
            console.error("ApiCallerLog: Exception on API call:", t)
        }
    }

    companion object {
        const val NOT_NULL_BODY_EXPECTED_MESSAGE = "Not null or empty body response was expected!"
        const val NULL_BODY_EXPECTED_MESSAGE = "Empty body response was expected!"
    }
}

class EmptyByteArrayResponseBodyException(message: String) : RuntimeException(message)
class NonEmptyByteArrayResponseBodyException(message: String) : RuntimeException(message)