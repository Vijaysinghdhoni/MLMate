package com.vsdhoni5034.mlmate.presentation.landMarkRecoginzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.vsdhoni5034.mlmate.domain.model.Classification
import com.vsdhoni5034.mlmate.domain.LandMarkClassifier
import com.vsdhoni5034.mlmate.presentation.util.centerCrop

class LandMarkImageAnalyzer(
    private val classifier: LandMarkClassifier,
    private val onResult: (List<Classification>) -> Unit
) : ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0
    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)

            val results = classifier.classify(bitmap, rotationDegrees)
            onResult(results)
        }
        frameSkipCounter++
        image.close()

    }
}