package com.kto.employenexa.ui.operation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employenexa.domain.models.EmployeModel
import com.kto.employenexa.domain.usecase.employe.CreateEmploye
import com.kto.employenexa.domain.usecase.employe.GetEmployeId
import com.kto.employenexa.domain.usecase.employe.UpdateEmploye
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateUpdateViewModel @Inject constructor(
    private val createEmploye: CreateEmploye,
    private val getEmployeId: GetEmployeId,
    private val updateEmploye: UpdateEmploye,
) : ViewModel() {
    private var _state = MutableStateFlow<CreateUpdateState>(CreateUpdateState.Init)
    val state: StateFlow<CreateUpdateState> = _state

    fun createEmployes(employeModel: EmployeModel) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                // hilo secundario
                createEmploye(employeModel)
            }
            if (result != null) {
                _state.value = CreateUpdateState.Success(result)
            } else {
                _state.value = CreateUpdateState.Error("Error :((")
            }
        }
    }

    fun searchEmployeId(id: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getEmployeId(id)
            }
            if (result != null) {
                _state.value = CreateUpdateState.Success(result)
            } else {
                _state.value = CreateUpdateState.Error("Error :((")
            }
        }
    }

    fun updateEmployesId(employeModel: EmployeModel, id: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                // hilo secundario
                updateEmploye(employeModel, id)
            }
            if (result != null) {
                _state.value = CreateUpdateState.Loading
            } else {
                _state.value = CreateUpdateState.Error("Error :((")
            }
        }
    }
}