package com.vsdhoni5034.mlmate.presentation.authentication.login

sealed class LoginEvent {

    data object loginButtonClicked : LoginEvent()

    data class EmailEntered(val email: String) : LoginEvent()

    data class PasswordEntered(val password: String) : LoginEvent()

    data object PasswordToggleClicked : LoginEvent()
}