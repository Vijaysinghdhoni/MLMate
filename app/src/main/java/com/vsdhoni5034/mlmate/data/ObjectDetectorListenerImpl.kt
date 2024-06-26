package com.vsdhoni5034.mlmate.data

import com.vsdhoni5034.mlmate.domain.ObjectDetectorListener
import com.vsdhoni5034.mlmate.domain.model.AnalysisResult

class ObjectDetectorListenerImpl(
    val onErrorCallback: (error: String, errorCode: Int) -> Unit,
    val onResultsCallback: (result: AnalysisResult) -> Unit
) : ObjectDetectorListener {
    override fun onError(error: String, errorCode: Int) {
        onErrorCallback(error, errorCode)
    }

    override fun onResult(analysisResult: AnalysisResult) {
        onResultsCallback(analysisResult)
    }
}