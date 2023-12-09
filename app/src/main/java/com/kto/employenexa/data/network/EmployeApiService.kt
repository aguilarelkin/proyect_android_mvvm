package com.kto.employenexa.data.network

import com.kto.employenexa.data.network.response.EmployeResponse
import com.kto.employenexa.domain.models.EmployeModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EmployeApiService {

    @GET("/api/v1/employe")
    suspend fun getEmployes(): List<EmployeResponse>

    @GET("/api/v1/employe/{id}")
    suspend fun getEmployeId(@Path("id") id: String): EmployeResponse

    @POST("/api/v1/employe")
    suspend fun createEmploye(@Body employeModel: EmployeModel): EmployeResponse

    @PUT("/api/v1/employe/{id}")
    suspend fun updateEmploye(
        @Body employeModel: EmployeModel,
        @Path("id") id: String
    ): EmployeResponse

    @DELETE("/api/v1/employe/{id}")
    suspend fun deleteEmploye(@Path("id") id: String)
}