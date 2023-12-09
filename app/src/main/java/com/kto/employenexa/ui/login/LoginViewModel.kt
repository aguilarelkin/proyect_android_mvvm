package com.kto.employenexa.ui.login

import android.content.Context
import android.util.Patterns
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kto.employenexa.R
import com.kto.employenexa.domain.models.LoginModel
import com.kto.employenexa.domain.usecase.login.LoginUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUc: LoginUC) : ViewModel() {

    private val _loginForm = MutableStateFlow<LoginFormState>(LoginFormState.InitLd)
    val loginFormState: StateFlow<LoginFormState> = _loginForm

    private val _loginResult = MutableStateFlow<LoginResult>(LoginResult.Init)
    val loginResult: StateFlow<LoginResult> = _loginResult

    fun login(loginModel: LoginModel) {

        // can be launched in a separate asynchronous job
        viewModelScope.launch {

            val result = withContext(Dispatchers.IO) {
                loginUc(loginModel)
            }

            if (result != null) {
                _loginResult.value = LoginResult.Success(result)
            } else {
                _loginResult.value = LoginResult.Error(R.string.login_failed)
            }
        }
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                LoginFormState.UsernameError(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState.PasswordError(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState.IsDataValid(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 3
    }
}