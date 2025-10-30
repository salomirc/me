package org.example.me.api_caller

import com.varabyte.kobweb.browser.http.FetchDefaults
import com.varabyte.kobweb.browser.http.HttpMethod
import kotlinx.coroutines.CompletableDeferred
import org.khronos.webgl.Int8Array
import org.khronos.webgl.get
import org.w3c.dom.Window
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestRedirect
import org.w3c.fetch.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.js.json


/**
 * Returns the current body of the target [Response].
 *
 * Note that the returned bytes could be an empty array, which could mean the body wasn't set OR that it was set to
 * the empty string.
 */
suspend fun Response.localGetBodyBytes(): ByteArray {
    return suspendCoroutine { cont ->
        this.arrayBuffer().then { responseBuffer ->
            val int8Array = Int8Array(responseBuffer)
            cont.resume(ByteArray(int8Array.length) { i -> int8Array[i] })
        }.catch {
            cont.resume(ByteArray(0))
        }
    }
}

/**
 * A Kotlin-idiomatic version of the standard library's [Window.fetch] function.
 *
 * @param headers An optional map of headers to send with the request. Note: If a body is specified, the
 *   `Content-Length` header will be automatically set. However, any headers set manually will always take precedence.
 */
suspend fun Window.fetchResponse(
    method: HttpMethod,
    resource: String,
    headers: Map<String, Any>? = FetchDefaults.Headers,
    body: ByteArray? = null,
    redirect: RequestRedirect? = FetchDefaults.Redirect,
    abortController: LocalAbortController? = null
): Response {
    val responseDeferred = CompletableDeferred<Response>()
    val headersJson = if (!headers.isNullOrEmpty() || body != null) {
        json().apply {
            if (body != null) {
                this["Content-Length"] = body.size
            }
            headers?.let { headers ->
                for ((key, value) in headers) {
                    this[key] = value
                }
            }
        }
    } else null

    val requestInit = RequestInit(
        method = method.name,
        headers = headersJson ?: undefined,
        body = body ?: undefined,
        redirect = redirect ?: undefined,
    )
    if (abortController != null) {
        // Hack: Workaround since Compose HTML's `RequestInit` doesn't have a `signal` property
        val requestInitDynamic: dynamic = requestInit
        requestInitDynamic["signal"] = abortController.signal
    }

    window.fetch(resource, requestInit).then(
        onFulfilled = { res -> responseDeferred.complete(res) },
        onRejected = { t -> responseDeferred.completeExceptionally(t) }
    )

    return responseDeferred.await()
}

/**
 * A class which can be used to abort an API request after it was made.
 *
 * ```
 * var abortController: AbortController? by remember { mutableStateOf(null) }
 * LaunchedEffect(Unit) {
 *   abortController = AbortController()
 *   val result = window.http.get("/some/api/path", abortController = abortController)
 *   abortController = null
 * }
 *
 * Button(onClick = { abortController?.abort() }) {
 *   Text("Abort")
 * }
 * ```
 *
 * Note that if you re-use the same abort controller across multiple requests, one abort call will abort them all. And
 * if you pass an already aborted controller into a new request, it will fail immediately.
 */
class LocalAbortController {
    private val controller = js("new AbortController()")

    internal val signal = controller.signal

    fun abort() {
        controller.abort()
    }
}