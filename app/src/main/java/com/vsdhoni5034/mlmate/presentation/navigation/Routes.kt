package com.vsdhoni5034.mlmate.presentation.navigation

sealed class Routes(val route:String) {

    data object LandMarkRecognizerScreen : Routes("LANDMARK_RECOGNIZER_SCREEN")

    data object ObjectDetectionScreen : Routes("OBJECT_DETECTION_SCREEN")

    data object OptionsScreen : Routes("OPTIONS_SCREEN")

}