package com.vsdhoni5034.mlmate.presentation.authentication.login

data class LoginLocalState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val emailError: String = "",
    val passwordError: String = ""
)
