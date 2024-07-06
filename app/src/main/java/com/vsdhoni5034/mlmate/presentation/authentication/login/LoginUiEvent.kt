package com.vsdhoni5034.mlmate.presentation.authentication.login

sealed class LoginUiEvent {

    data class ShowMessage(val message:String) : LoginUiEvent()

    data object NavigationEvent : LoginUiEvent()

}