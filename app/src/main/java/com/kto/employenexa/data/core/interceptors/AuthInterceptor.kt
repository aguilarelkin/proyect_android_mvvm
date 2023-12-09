package com.kto.employenexa.data.core.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        val tokenData: String? = tokenManager.getToken()
        val request = if (tokenData != null) {
            chain.request().newBuilder().header("Authorization", "Bearer $tokenData")
                .build()
        } else {
            chain.request().newBuilder().build()
        }
        return chain.proceed(request)
    }

}