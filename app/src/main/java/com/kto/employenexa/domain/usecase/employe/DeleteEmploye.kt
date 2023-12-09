package com.kto.employenexa.domain.usecase.employe

import com.kto.employenexa.domain.Repository
import javax.inject.Inject

class DeleteEmploye @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: String) = repository.deleteEmploye(id)

}