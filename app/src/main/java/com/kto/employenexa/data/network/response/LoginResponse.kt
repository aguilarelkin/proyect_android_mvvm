package com.kto.employenexa.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Username") val username: String,
    @SerializedName("token") val token: String
)
