package com.kto.employenexa.domain.usecase.employe

import com.kto.employenexa.domain.Repository
import com.kto.employenexa.domain.models.EmployeModel
import javax.inject.Inject

class UpdateEmploye @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(employeModel: EmployeModel, id: String) =
        repository.updateEmploye(employeModel, id)

}