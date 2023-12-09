package com.kto.employenexa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kto.employenexa.domain.models.ServicemModel

data class ServicemResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("service") val service: String,
    @SerializedName("price") val price: Long,
    @SerializedName("base") val base: String
) {
    fun toDomain() = ServicemModel(
        id, service, price, base
    )
}