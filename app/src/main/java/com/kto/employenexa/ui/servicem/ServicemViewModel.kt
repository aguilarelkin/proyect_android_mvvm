package com.kto.employenexa.ui.servicem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employenexa.domain.models.ServicemModel
import com.kto.employenexa.domain.usecase.servicem.DeleteServicem
import com.kto.employenexa.domain.usecase.servicem.GetServicem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServicemViewModel @Inject constructor(
    private val getServicem: GetServicem,
    private val deleteServicem: DeleteServicem
) : ViewModel() {

    private var _state = MutableStateFlow<ServicemState>(ServicemState.Loading)
    val state: StateFlow<ServicemState> = _state

    fun getServicemList() {
        viewModelScope.launch {
            _state.value = ServicemState.Loading
            val result: List<ServicemModel>? = withContext(Dispatchers.IO) {
                getServicem()
            }
            if (result != null) {
                _state.value = ServicemState.Success(result)
            } else {
                _state.value = ServicemState.Error("Error :((")
            }
        }
    }

    fun deleteServicemIds(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteServicem(id)
            }
            _state.value = ServicemState.Loading
            _state.value = ServicemState.Success(getServicem()!!)
        }
    }


}