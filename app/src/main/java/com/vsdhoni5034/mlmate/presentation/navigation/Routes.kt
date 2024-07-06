package com.vsdhoni5034.mlmate.presentation.navigation

sealed class Routes(val route: String) {

    data object LandMarkRecognizerScreen : Routes("LANDMARK_RECOGNIZER_SCREEN")

    data object ObjectDetectionScreen : Routes("OBJECT_DETECTION_SCREEN")

    data object OptionsScreen : Routes("OPTIONS_SCREEN")

    data object LoginScreen : Routes("LOGIN_SCREEN")

    data object RegisterScreen : Routes("REGISTER_SCREEN")

    data object LoadingScreen : Routes("LOADING_SCREEN")


    data object AuthNav : Routes("AUTH_NAV_GRAPH")

    data object MlAppNav : Routes("ML_NAV_GRAPH")
}