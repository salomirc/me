package org.example.me.repositories

import com.varabyte.kobweb.browser.http.HttpMethod
import kotlinx.browser.window
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.example.me.api_caller.IWebApiCaller
import org.example.me.api_caller.fetchResponse
import org.example.me.helpers.toSendMailEndpointUrl
import org.example.me.models.data.SendMailRequestDto
import org.example.me.models.data.SendMailResponseDto
import org.example.me.models.domain.SendMailRequestModel
import org.example.me.models.domain.SendMailResponseModel
import org.example.me.repositories.ResponseState.ActiveResponseState

interface ISendMailRepository {
    suspend fun sendMail(sendMailRequestModel: SendMailRequestModel): Flow<ActiveResponseState<SendMailResponseModel>>
}

class SendMailRepository(
    private val apiCaller: IWebApiCaller
): ISendMailRepository {

    override suspend fun sendMail(
        sendMailRequestModel: SendMailRequestModel
    ): Flow<ActiveResponseState<SendMailResponseModel>> {
        val json: String = Json.encodeToString<SendMailRequestDto>(sendMailRequestModel.toDataModel())
        val bodyRequest = json.encodeToByteArray()

        return DataSourcePattern.singlePattern {
            apiCaller.invoke {
                window.fetchResponse(
                    method = HttpMethod.POST,
                    headers = mapOf(Pair("Content-Type", "application/json")),
                    body = bodyRequest,
                    resource = "/sendMail".toSendMailEndpointUrl())
            }
                .mapCatching { json ->
                    val responseDto = Json.decodeFromString<SendMailResponseDto>(json)
                    responseDto.toDomainModel()
                }
        }
    }
}