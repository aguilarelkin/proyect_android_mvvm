package com.kto.employenexa.domain.usecase.employe

import com.kto.employenexa.domain.Repository
import com.kto.employenexa.domain.models.EmployeModel
import javax.inject.Inject

class CreateEmploye @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(employeModel: EmployeModel): EmployeModel? {
        return repository.createEmploye(employeModel)
    }
}