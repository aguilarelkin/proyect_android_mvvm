package com.kto.employenexa.domain.usecase.servicem

import com.kto.employenexa.domain.RepositoryServicem
import com.kto.employenexa.domain.models.ServicemModel
import javax.inject.Inject

class CreateServicem @Inject constructor(private val repositoryServicem: RepositoryServicem) {
    suspend operator fun invoke(servicemModel: ServicemModel) =
        repositoryServicem.createServicem(servicemModel)
}