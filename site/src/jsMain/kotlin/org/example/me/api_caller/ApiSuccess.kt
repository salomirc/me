package org.example.me.api_caller

import org.w3c.fetch.Headers
import org.w3c.fetch.Response


/**
 * A wrapper of a successful (200..399) HTTP response.
 */
data class ApiSuccess<T>(
    val code: Int,
    val message: String? = null,
    val headers: Headers,
    val body: T,
    val rawResponse: Response
)
