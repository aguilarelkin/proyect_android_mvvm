package com.kto.employenexa.data

import android.util.Log
import com.kto.employenexa.data.network.EmployeApiService
import com.kto.employenexa.domain.Repository
import com.kto.employenexa.domain.models.EmployeModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: EmployeApiService) : Repository {
    override suspend fun getEmployes(): List<EmployeModel>? {
        //peticionRetrofit
        runCatching {//ejecutar una tarea
            apiService.getEmployes()
        }.onSuccess {
            return it.map { data -> data.toDomain() } //4:41:00
        }.onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }

    override suspend fun getEmployeId(id: String): EmployeModel? {
        runCatching { apiService.getEmployeId(id) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }

    override suspend fun createEmploye(employeModel: EmployeModel): EmployeModel? {
        runCatching {
            apiService.createEmploye(employeModel)
        }.onSuccess { return it.toDomain() }.onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }

    override suspend fun updateEmploye(employeModel: EmployeModel, id: String): EmployeModel? {
        runCatching { apiService.updateEmploye(employeModel, id) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }

    override suspend fun deleteEmploye(id: String): String {
        runCatching { apiService.deleteEmploye(id) }
            .onSuccess { return "Delete Correct" }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return ""
    }
}

