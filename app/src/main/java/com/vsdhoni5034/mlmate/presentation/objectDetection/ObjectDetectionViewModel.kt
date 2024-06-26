package com.vsdhoni5034.mlmate.presentation.objectDetection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult

class ObjectDetectionViewModel : ViewModel() {

    var results by mutableStateOf<ObjectDetectorResult?>(null)
        private set

    var frameWidth by mutableIntStateOf(3)
        private set

    var frameHeight by mutableIntStateOf(4)
        private set

    var active by mutableStateOf(true)


    fun updateResults(results: ObjectDetectorResult?) {
        this.results = results
    }

    fun updateActive(boolean: Boolean) {
        active = boolean
    }

    fun updateFrameHeight(height: Int) {
        frameHeight = height
    }

    fun updateFrameWidth(width: Int) {
        frameWidth = width
    }

}