package com.kto.employenexa.ui.operationser

import com.kto.employenexa.domain.models.ServicemModel

sealed class CreUServiceState {
    data object Loading : CreUServiceState()
    data class Error(val error: String) : CreUServiceState()
    data class Success(val data: ServicemModel) : CreUServiceState()
    data object Init : CreUServiceState()
}
