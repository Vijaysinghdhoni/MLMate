package com.vsdhoni5034.mlmate.domain

import android.graphics.Bitmap
import com.vsdhoni5034.mlmate.domain.model.Classification

interface LandMarkClassifier {

    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>

}