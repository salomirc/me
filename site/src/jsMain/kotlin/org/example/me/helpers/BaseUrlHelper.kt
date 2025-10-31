package org.example.me.helpers

private const val BASE_URL: String = "https://jsonplaceholder.typicode.com"
private const val SEND_MAIL_BASE_URL: String = "http://localhost:8889"

fun String.toEndpointUrl() = "$BASE_URL$this"
fun String.toSendMailEndpointUrl() = "$SEND_MAIL_BASE_URL$this"