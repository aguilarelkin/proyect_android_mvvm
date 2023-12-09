package com.kto.employenexa.domain.usecase.employe

import com.kto.employenexa.domain.Repository
import javax.inject.Inject

class GetEmploye @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke() =
        repository.getEmployes() //operator sobre escribir funciones de la clase

}