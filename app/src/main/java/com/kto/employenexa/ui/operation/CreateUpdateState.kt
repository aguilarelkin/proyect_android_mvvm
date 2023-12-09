package com.kto.employenexa.ui.operation

import com.kto.employenexa.domain.models.EmployeModel

sealed class CreateUpdateState {
    data object Loading : CreateUpdateState()
    data class Error(val error: String) : CreateUpdateState()
    data class Success(val data: EmployeModel) : CreateUpdateState()
    data object Init : CreateUpdateState()
}