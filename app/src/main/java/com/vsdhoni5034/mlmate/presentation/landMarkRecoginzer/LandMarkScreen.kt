package com.vsdhoni5034.mlmate.presentation.landMarkRecoginzer

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vsdhoni5034.mlmate.domain.model.Classification
import com.vsdhoni5034.mlmate.presentation.landMarkRecoginzer.components.CameraPreview

@Composable
fun LandMarkScreen(
    modifier: Modifier = Modifier,
    controller: LifecycleCameraController,
    classifications: List<Classification>
) {


    Box(
        modifier = modifier.fillMaxSize()
    ) {

        CameraPreview(controller = controller, Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            classifications.forEach {
                Text(
                    text = it.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp))
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }


}