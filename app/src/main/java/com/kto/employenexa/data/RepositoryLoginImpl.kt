package com.kto.employenexa.data

import android.util.Log
import com.kto.employenexa.data.core.datastore.DataInfo
import com.kto.employenexa.data.network.LoginApiService
import com.kto.employenexa.domain.RepositoryLogin
import com.kto.employenexa.domain.models.LoginModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RepositoryLoginImpl @Inject constructor(
    private val loginApiService: LoginApiService,
    private val dataInfo: DataInfo
) :
    RepositoryLogin {

    override suspend fun loginPost(loginModel: LoginModel): String? {
        runCatching {
            loginApiService.loginService(loginModel)
        }.onSuccess {
            dataInfo.saveToken(it.token)
            Log.i("datas", it.token)
            Log.i("datas", dataInfo.getSettings().first().toString())

            return it.token
        }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }
}