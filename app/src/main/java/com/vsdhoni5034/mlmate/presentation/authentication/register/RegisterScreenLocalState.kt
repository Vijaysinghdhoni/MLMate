package com.vsdhoni5034.mlmate.presentation.authentication.register

data class RegisterScreenLocalState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isConfirmPasswordError: Boolean = false,
    val emailError: String = "",
    val passwordError: String = "",
    val confirmPasswordError : String = ""
)
