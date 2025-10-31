package org.example.me.models.data

import kotlinx.serialization.Serializable
import org.example.me.models.domain.SendMailResponseModel

@Serializable
data class SendMailResponseDto(
    val isSuccess: Boolean,
    val emailId: String,
    val errorMessage: String
) {
    fun toDomainModel(): SendMailResponseModel {
        return SendMailResponseModel(
            isSuccess = isSuccess,
            emailId = emailId,
            errorMessage = errorMessage
        )
    }
}