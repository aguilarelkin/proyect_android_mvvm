package com.kto.employenexa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kto.employenexa.domain.models.EmployeModel

data class EmployeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("firstname") val firstname: String,
    @SerializedName("lastname") val lastname: String,
    @SerializedName("mail") val mail: String,
    @SerializedName("password") val password: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("base") val base: String,
    @SerializedName("role") val role: List<RoleResponse>,
    @SerializedName("telephone") val telephone: String
) {
    fun toDomain() = EmployeModel(
        id = id,
        firstname = firstname,
        lastname = lastname,
        password = "",
        mail = mail,
        avatar = avatar,
        base = base,
        role = role.map { it.toDomain() },
        telephone = telephone
    )

}