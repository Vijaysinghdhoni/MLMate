package com.vsdhoni5034.mlmate.domain.model

import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult

data class AnalysisResult(
    val results: List<ObjectDetectorResult>,
    val inferenceTime: Long,
    val inputImageWidth: Int,
    val inputImageHeight: Int
)
