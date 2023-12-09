package com.kto.employenexa.data.network

import com.kto.employenexa.data.network.response.ServicemResponse
import com.kto.employenexa.domain.models.ServicemModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServicemApiService {

    @GET("/api/v1/servicem")
    suspend fun getServicem(): List<ServicemResponse>

    @GET("/api/v1/servicem/{id}")
    suspend fun getServicemId(@Path("id") id: String): ServicemResponse

    @POST("/api/v1/servicem")
    suspend fun createServicem(@Body servicemModel: ServicemModel): ServicemResponse

    @PUT("/api/v1/servicem/{id}")
    suspend fun updateServicem(
        @Body servicemModel: ServicemModel,
        @Path("id") id: String
    ): ServicemResponse

    @DELETE("/api/v1/servicem/{id}")
    suspend fun deleteServicem(@Path("id") id: String)


}