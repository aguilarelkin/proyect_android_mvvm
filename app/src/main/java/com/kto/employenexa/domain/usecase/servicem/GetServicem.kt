package com.kto.employenexa.domain.usecase.servicem

import com.kto.employenexa.domain.RepositoryServicem
import javax.inject.Inject

class GetServicem @Inject constructor(private val repositoryServicem: RepositoryServicem) {
    suspend operator fun invoke() = repositoryServicem.getServicem()
}