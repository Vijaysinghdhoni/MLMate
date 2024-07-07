package com.vsdhoni5034.mlmate.presentation.optionsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsdhoni5034.mlmate.data.UserPreferences
import com.vsdhoni5034.mlmate.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OptionsViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _optionsUiEvent = MutableSharedFlow<OptionsUiEvent>()
    val optionsUiEvent = _optionsUiEvent.asSharedFlow()

    fun onLogoutClick() {
        viewModelScope.launch {
            authenticationRepository.logoutUser()
            updateUserLoginState(false)
            _optionsUiEvent.emit(OptionsUiEvent.NavigationEvent)
        }
    }
    private fun updateUserLoginState(isLogin: Boolean) {
        viewModelScope.launch {
            userPreferences.writeIntPref(isLogin)
        }
    }

}