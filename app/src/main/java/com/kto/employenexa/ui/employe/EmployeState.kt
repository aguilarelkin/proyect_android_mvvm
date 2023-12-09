package com.kto.employenexa.ui.employe

import com.kto.employenexa.domain.models.EmployeModel

sealed class EmployeState {
    data object Loading : EmployeState()
    data class Error(val error: String) : EmployeState()
    data class Success(val data: List<EmployeModel>) : EmployeState()
}