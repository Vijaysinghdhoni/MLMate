package com.vsdhoni5034.mlmate.presentation.authentication.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsdhoni5034.mlmate.data.util.Resource
import com.vsdhoni5034.mlmate.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    var registerLocalState by mutableStateOf(RegisterScreenLocalState())
        private set

    var registerState by mutableStateOf(RegisterState())
        private set


    private val _registerUiEvent = MutableSharedFlow<RegisterUiEvent>()
    val registerUiEvent = _registerUiEvent.asSharedFlow()


    fun onEvent(event: RegisterEvent) {

        when (event) {

            is RegisterEvent.EmailEntered -> {
                registerLocalState = registerLocalState.copy(
                    email = event.email
                )
            }

            is RegisterEvent.PasswordEntered -> {
                registerLocalState = registerLocalState.copy(
                    password = event.password
                )
            }

            is RegisterEvent.ConfirmPasswordEntered -> {
                registerLocalState = registerLocalState.copy(
                    confirmPassword = event.password
                )
            }

            is RegisterEvent.PasswordToggleClicked -> {
                registerLocalState = registerLocalState.copy(
                    isPasswordVisible = !registerLocalState.isPasswordVisible
                )
            }

            is RegisterEvent.ConfirmPasswordToggleClicked -> {
                registerLocalState = registerLocalState.copy(
                    isConfirmPasswordVisible = !registerLocalState.isConfirmPasswordVisible
                )
            }

            is RegisterEvent.RegisterButtonClicked -> {
                registerUser()
            }
        }

    }

    private fun registerUser() {

        viewModelScope.launch {

            if (registerLocalState.email.isEmpty()) {
                registerLocalState = registerLocalState.copy(
                    isEmailError = !registerLocalState.isEmailError,
                    emailError = "Email cannot be empty!"
                )
                registerState = registerState.copy(
                    loading = false
                )
                _registerUiEvent.emit(RegisterUiEvent.ShowMessage("Email cannot be empty!"))
                return@launch
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(registerLocalState.email).matches()) {
                registerLocalState = registerLocalState.copy(
                    isEmailError = !registerLocalState.isEmailError,
                    emailError = "Email format is invalid!"
                )
                registerState = registerState.copy(
                    loading = false
                )
                _registerUiEvent.emit(RegisterUiEvent.ShowMessage("Email format is invalid!"))
                return@launch
            }

            if (registerLocalState.password.isEmpty() || registerLocalState.password.length < 6) {
                registerLocalState = registerLocalState.copy(
                    isPasswordError = !registerLocalState.isPasswordError,
                    passwordError = "Password should have minimum 6 characters!"
                )
                registerState = registerState.copy(
                    loading = false
                )
                _registerUiEvent.emit(
                    RegisterUiEvent.ShowMessage("Password should have minimum 6 char!")
                )
                return@launch
            }

            if (registerLocalState.confirmPassword != registerLocalState.password) {

                registerLocalState = registerLocalState.copy(
                    isConfirmPasswordVisible = !registerLocalState.isConfirmPasswordError,
                    confirmPasswordError = "Confirm Password doesn't matches with Password"
                )
                registerState = registerState.copy(
                    loading = false
                )
                _registerUiEvent.emit(
                    RegisterUiEvent.ShowMessage("Confirm Password doesn't matches with Password")
                )
                return@launch
            }

            registerState = registerState.copy(
                loading = true
            )

            val response = authenticationRepository.registerUser(
                email = registerLocalState.email,
                password = registerLocalState.password
            )

            when (response) {

                is Resource.Success -> {
                    registerState = registerState.copy(
                        loading = false
                    )
                    _registerUiEvent.emit(RegisterUiEvent.Navigate)
                }

                is Resource.Error -> {
                    registerState = registerState.copy(
                        loading = false
                    )
                    _registerUiEvent.emit(
                        RegisterUiEvent.ShowMessage(
                            response.message ?: "Some thing went wrong! try again later!"
                        )
                    )
                }
            }

        }

    }

}