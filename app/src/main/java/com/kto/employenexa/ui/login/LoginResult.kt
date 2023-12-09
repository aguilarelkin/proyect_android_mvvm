package com.kto.employenexa.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
sealed class LoginResult {
    data object Init : LoginResult()
    data class Success(val success: String) : LoginResult()
    data class Error(val error: Int) : LoginResult()
}