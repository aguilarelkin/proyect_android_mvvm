package com.kto.employenexa.data

import android.util.Log
import com.kto.employenexa.data.network.ServicemApiService
import com.kto.employenexa.domain.RepositoryServicem
import com.kto.employenexa.domain.models.ServicemModel
import javax.inject.Inject

class RepositoryServicemImpl @Inject constructor(private val servicemApiService: ServicemApiService) :
    RepositoryServicem {
    override suspend fun getServicem(): List<ServicemModel>? {
        runCatching { servicemApiService.getServicem() }
            .onSuccess {
                return it.map { data -> data.toDomain() }
            }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }

    override suspend fun getServicemId(id: String): ServicemModel? {
        runCatching { servicemApiService.getServicemId(id) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }

    override suspend fun createServicem(servicemModel: ServicemModel): ServicemModel? {
        runCatching { servicemApiService.createServicem(servicemModel) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }

    override suspend fun updateServicem(servicemModel: ServicemModel, id: String): ServicemModel? {
        runCatching { servicemApiService.updateServicem(servicemModel, id) }
            .onSuccess {
                return it.toDomain()
            }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return null
    }

    override suspend fun deleteServicem(id: String): String {
        runCatching { servicemApiService.deleteServicem(id) }
            .onSuccess { return "Delete ok" }
            .onFailure { Log.i("server 4", "Error: ${it.message}") }
        return ""
    }
}