package org.example.me.models.domain

import org.example.me.models.data.SendMailRequestDto

data class SendMailRequestModel(
    val name: String,
    val email: String,
    val message: String
) {
    fun toDataModel(): SendMailRequestDto {
        return SendMailRequestDto(
            name = name,
            email = email,
            message = message
        )
    }
}
