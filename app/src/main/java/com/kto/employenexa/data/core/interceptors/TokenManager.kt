package com.kto.employenexa.data.core.interceptors

import com.kto.employenexa.data.core.datastore.DataInfo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TokenManager @Inject constructor(private val dataInfo: DataInfo) {

    fun getToken(): String? {
        val data = runBlocking {
            dataInfo.getSettings().first()
        }
        return data
    }
}