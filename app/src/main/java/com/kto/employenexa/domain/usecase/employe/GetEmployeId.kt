package com.kto.employenexa.domain.usecase.employe

import com.kto.employenexa.domain.Repository
import javax.inject.Inject

class GetEmployeId @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id: String) = repository.getEmployeId(id)

}