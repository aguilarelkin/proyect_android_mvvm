package com.kto.employenexa.domain

import com.kto.employenexa.domain.models.ServicemModel

interface RepositoryServicem {
    suspend fun getServicem(): List<ServicemModel>?
    suspend fun getServicemId(id: String): ServicemModel?
    suspend fun createServicem(servicemModel: ServicemModel): ServicemModel?
    suspend fun updateServicem(servicemModel: ServicemModel, id: String): ServicemModel?
    suspend fun deleteServicem(id: String): String
}