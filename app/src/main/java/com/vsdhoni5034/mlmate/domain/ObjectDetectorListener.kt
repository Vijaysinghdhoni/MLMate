package com.vsdhoni5034.mlmate.domain

import com.vsdhoni5034.mlmate.domain.model.AnalysisResult

interface ObjectDetectorListener {

    fun onError(error: String, errorCode: Int = 0)

    fun onResult(analysisResult: AnalysisResult)

}