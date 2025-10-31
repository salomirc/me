package org.example.me.models.domain

data class SendMailResponseModel(
    val isSuccess: Boolean,
    val emailId: String,
    val errorMessage: String
)