package com.kto.employenexa.data.network

import com.kto.employenexa.data.network.response.LoginResponse
import com.kto.employenexa.domain.models.LoginModel
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/login")
    suspend fun loginService(@Body loginModel: LoginModel): LoginResponse
}