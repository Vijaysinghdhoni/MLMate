package com.vsdhoni5034.mlmate.presentation.authentication.register

sealed class RegisterEvent {

    data class EmailEntered(val email: String) : RegisterEvent()

    data class PasswordEntered(val password: String) : RegisterEvent()

    data class ConfirmPasswordEntered(val password: String) : RegisterEvent()

    data object PasswordToggleClicked : RegisterEvent()

    data object ConfirmPasswordToggleClicked : RegisterEvent()

    data object RegisterButtonClicked : RegisterEvent()
}