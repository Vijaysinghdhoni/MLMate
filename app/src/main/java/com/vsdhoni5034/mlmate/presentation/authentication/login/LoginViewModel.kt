package com.vsdhoni5034.mlmate.presentation.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsdhoni5034.mlmate.data.UserPreferences
import com.vsdhoni5034.mlmate.data.util.Resource
import com.vsdhoni5034.mlmate.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    var loginLocalState by mutableStateOf(LoginLocalState())
        private set

    var loginScreenState by mutableStateOf(LoginScreenState())
        private set


    private val _loginUiEvent = MutableSharedFlow<LoginUiEvent>()
    val loginUiEvent = _loginUiEvent.asSharedFlow()


    fun onEvent(loginEvent: LoginEvent) {

        when (loginEvent) {

            is LoginEvent.loginButtonClicked -> {
                login()
            }

            is LoginEvent.EmailEntered -> {
                loginLocalState = loginLocalState.copy(
                    email = loginEvent.email
                )
            }

            is LoginEvent.PasswordEntered -> {
                loginLocalState = loginLocalState.copy(
                    password = loginEvent.password
                )
            }

            is LoginEvent.PasswordToggleClicked -> {
                loginLocalState = loginLocalState.copy(
                    isPasswordVisible = !loginLocalState.isPasswordVisible
                )
            }

        }

    }

    private fun login() {

        viewModelScope.launch {

            if (loginLocalState.email.isEmpty()) {
                loginLocalState = loginLocalState.copy(
                    isEmailError = !loginLocalState.isEmailError,
                    emailError = "Email cannot be empty!"
                )
                loginScreenState = loginScreenState.copy(
                    isLoading = false
                )
                _loginUiEvent.emit(
                    LoginUiEvent.ShowMessage("Email cannot be empty!")
                )
                return@launch
            }

            if (loginLocalState.password.isEmpty()) {
                loginLocalState = loginLocalState.copy(
                    isPasswordError = !loginLocalState.isPasswordError,
                    passwordError = "Password cannot be empty"
                )
                loginScreenState = loginScreenState.copy(
                    isLoading = false
                )
                _loginUiEvent.emit(
                    LoginUiEvent.ShowMessage("Password cannot be empty!")
                )
                return@launch
            }

            loginScreenState = loginScreenState.copy(
                isLoading = true
            )

            val response = authenticationRepository.loginUser(
                email = loginLocalState.email,
                password = loginLocalState.password
            )

            when (response) {

                is Resource.Success -> {
                    updateUserLoginState(true)
                    loginScreenState = loginScreenState.copy(isLoading = false)
                    _loginUiEvent.emit(LoginUiEvent.NavigationEvent)
                }

                is Resource.Error -> {
                    loginScreenState = loginScreenState.copy(isLoading = false)
                    _loginUiEvent.emit(
                        LoginUiEvent.ShowMessage(
                            response.message ?: "Some thing went wrong! try again later!"
                        )
                    )
                }
            }

        }

    }

    private fun updateUserLoginState(isLogin: Boolean) {
        viewModelScope.launch {
            userPreferences.writeIntPref(isLogin)
        }
    }

}
