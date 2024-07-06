package com.vsdhoni5034.mlmate.presentation.authentication.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsdhoni5034.mlmate.data.UserPreferences
import com.vsdhoni5034.mlmate.presentation.authentication.login.LoginUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingScreenViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _isUserLogin: MutableSharedFlow<Boolean?> = MutableSharedFlow()
    val isUserLogin = _isUserLogin.asSharedFlow()


    init {
        readUserLoginState()
    }


    private fun readUserLoginState() {
        viewModelScope.launch {
            userPreferences.readIntPref().collect {
                _isUserLogin.emit(it)
            }
        }
    }


}