package org.example.me.models.data

import kotlinx.serialization.Serializable
import org.example.me.models.domain.UserModel

@Serializable
data class UserResponseDto(
    val address: Address?,
    val company: Company?,
    val email: String?,
    val id: Int,
    val name: String?,
    val phone: String?,
    val username: String,
    val website: String?
) {
    @Serializable
    data class Address(
        val city: String?,
        val geo: Geo?,
        val street: String?,
        val suite: String?,
        val zipcode: String?
    ) {
        @Serializable
        data class Geo(
            val lat: String?,
            val lng: String?
        )
    }
    @Serializable
    data class Company(
        val bs: String?,
        val catchPhrase: String?,
        val name: String?
    )

    fun toDomainModel(): UserModel {
        return UserModel(
            address = UserModel.Address(
                city = address?.city ?: "",
                geo = UserModel.Address.Geo(
                    lat = address?.geo?.lat ?: "",
                    lng = address?.geo?.lng ?: ""
                ),
                street = address?.street ?: "",
                suite = address?.suite ?: "",
                zipcode = address?.zipcode ?: ""
            ),
            company = UserModel.Company(
                bs = company?.bs ?: "",
                catchPhrase = company?.catchPhrase ?: "",
                name = company?.name ?: ""
            ),
            email = email ?: "",
            id = id,
            name = name ?: "",
            phone = phone ?: "",
            username = username,
            website = website ?: ""
        )
    }
}