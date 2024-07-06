package com.vsdhoni5034.mlmate.presentation.authentication.register

sealed class RegisterUiEvent {

    data class ShowMessage(val message:String) :RegisterUiEvent()

    data object Navigate : RegisterUiEvent()

}