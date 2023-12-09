package com.kto.employenexa.domain

import com.kto.employenexa.domain.models.LoginModel

interface RepositoryLogin {

    suspend fun loginPost(loginModel: LoginModel): String?
}