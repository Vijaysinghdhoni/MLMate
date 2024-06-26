package com.vsdhoni5034.mlmate.presentation.landMarkRecoginzer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vsdhoni5034.mlmate.domain.model.Classification

class LandMarkViewModel : ViewModel() {

    var classifications by mutableStateOf<List<Classification>>(emptyList())
        private set


    fun updateClassifications(classification: List<Classification>) {
        classifications = classification
    }

}