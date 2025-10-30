package org.example.me.api_caller

import org.w3c.fetch.Response

class ApiException(
    val code: Int,
    val errorBodyString: String? = null,
    response: Response,
    bodyBytes: ByteArray?
) : RuntimeException(
    buildString {
        append("URL = ${response.url}, Status = ${response.status}, Status Text = ${response.statusText}")

        val bodyString = bodyBytes?.decodeToString()?.trim()?.takeIf { it.isNotBlank() }
        if (bodyString != null) {
            appendLine()
            val lines = bodyString.split("\n")
            val longestLineLength = lines.maxOfOrNull { it.length } ?: 0
            val indent = "  "
            val boundary = indent + "-".repeat(longestLineLength)
            appendLine(boundary)
            lines.forEach { line ->
                appendLine(indent + line)
            }
            appendLine(boundary)
        }
    }
)
