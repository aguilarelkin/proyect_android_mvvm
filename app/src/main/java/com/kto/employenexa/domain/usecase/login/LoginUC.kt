package com.kto.employenexa.domain.usecase.login

import com.kto.employenexa.domain.RepositoryLogin
import com.kto.employenexa.domain.models.LoginModel
import javax.inject.Inject

class LoginUC @Inject constructor(private val repositoryLogin: RepositoryLogin) {

    suspend operator fun invoke(loginModel: LoginModel) = repositoryLogin.loginPost(loginModel)
}