package com.kto.employenexa.ui.operationser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employenexa.domain.models.ServicemModel
import com.kto.employenexa.domain.usecase.servicem.CreateServicem
import com.kto.employenexa.domain.usecase.servicem.GetServicemId
import com.kto.employenexa.domain.usecase.servicem.UpdateServicem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreUpServiceViewModel @Inject constructor(
    private val createServicem: CreateServicem,
    private val getServicemId: GetServicemId,
    private val updateServicem: UpdateServicem
) : ViewModel() {

    private var _state = MutableStateFlow<CreUServiceState>(CreUServiceState.Init)
    val state: StateFlow<CreUServiceState> = _state


    fun createServicems(servicemModel: ServicemModel) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                // hilo secundario
                createServicem(servicemModel)
            }
            if (result != null) {
                _state.value = CreUServiceState.Success(result)
            } else {
                _state.value = CreUServiceState.Error("Error :((")
            }
        }
    }

    fun searchServicemIds(id: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getServicemId(id)
            }
            if (result != null) {
                _state.value = CreUServiceState.Success(result)
            } else {
                _state.value = CreUServiceState.Error("Error :((")
            }
        }
    }

    fun updateServicemIds(servicemModel: ServicemModel, id: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                // hilo secundario
                updateServicem(servicemModel, id)
            }
            if (result != null) {
                _state.value = CreUServiceState.Loading
            } else {
                _state.value = CreUServiceState.Error("Error :((")
            }
        }
    }

}