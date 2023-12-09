package com.kto.employenexa.ui.login

/**
 * Data validation state of the login form.
 */
sealed class LoginFormState {
    data class UsernameError(val usernameError: Int) : LoginFormState()
    data class PasswordError(val passwordError: Int) : LoginFormState()
    data class IsDataValid(val isDataValid: Boolean) : LoginFormState()
    data object InitLd : LoginFormState()
}