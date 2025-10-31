package org.example.me.models.data

import kotlinx.serialization.Serializable

@Serializable
data class SendMailRequestDto(
    val name: String,
    val email: String,
    val message: String
)