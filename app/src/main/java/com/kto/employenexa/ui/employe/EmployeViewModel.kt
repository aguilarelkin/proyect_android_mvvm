package com.kto.employenexa.ui.employe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employenexa.domain.models.EmployeModel
import com.kto.employenexa.domain.usecase.employe.DeleteEmploye
import com.kto.employenexa.domain.usecase.employe.GetEmploye
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel //para recibir cosas injectadas  además se puede inyectar reposi o el usecase
class EmployeViewModel @Inject constructor(
    private val getEmploye: GetEmploye, private val deleteEmploye: DeleteEmploye,
) : ViewModel() {
    /*
        private var _employe =
            MutableStateFlow<List<String>>(emptyList())//@DEprecate liveData -> stateFlow
        val employe: StateFlow<List<String>> = _employe

        init {
            _employe.value = listOf("hola","Holaaa", "hay","stephany valencia")
        }*///sin estados
    private var _state = MutableStateFlow<EmployeState>(EmployeState.Loading)
    val state: StateFlow<EmployeState> = _state

    fun getEmployes() {
        viewModelScope.launch {
            //hilo principal
            _state.value = EmployeState.Loading
            val result: List<EmployeModel>? = withContext(Dispatchers.IO) {
                getEmploye()  // hilo secundario
            }
            if (result != null) {
                _state.value = EmployeState.Success(result)
            } else {
                _state.value = EmployeState.Error("Error :((")
            }
            //hilo principal
        }
    }

    fun deleteEmployeId(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                deleteEmploye(id)
            }
            _state.value = EmployeState.Loading
            _state.value = EmployeState.Success(getEmploye()!!)
        }
    }
}