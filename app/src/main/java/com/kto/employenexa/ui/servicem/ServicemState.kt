package com.kto.employenexa.ui.servicem

import com.kto.employenexa.domain.models.ServicemModel

sealed class ServicemState {
    data object Loading : ServicemState()
    data class Error(val error: String) : ServicemState()
    data class Success(val data: List<ServicemModel>) : ServicemState()
}
