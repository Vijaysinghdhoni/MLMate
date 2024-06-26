package com.vsdhoni5034.mlmate.presentation.objectDetection.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.mediapipe.tasks.vision.objectdetector.ObjectDetectorResult
import com.vsdhoni5034.mlmate.ui.theme.Turquoise

@Composable
fun ResultOverlay(
    result: ObjectDetectorResult,
    frameWidth: Int,
    frameHeight: Int
) {
    val detections = result.detections()
    if (detections != null) {
        for (detection in detections) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val resultsBound = detection.boundingBox()
                val boxWidth = (resultsBound.width() / frameWidth) * this.maxWidth.value
                val boxHeight = (resultsBound.height() / frameHeight) * this.maxHeight.value
                val boxLeftOffset = (resultsBound.left / frameWidth) * this.maxWidth.value
                val boxTopOffset = (resultsBound.top / frameHeight) * this.maxHeight.value

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(boxLeftOffset.dp, boxTopOffset.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .border(3.dp, Turquoise)
                            .width(boxWidth.dp)
                            .height(boxHeight.dp)
                    )
                    Box(modifier = Modifier.padding(3.dp)) {
                        Text(
                            modifier = Modifier
                                .background(Color.Black)
                                .padding(5.dp, 0.dp),
                            text = "${
                                detection.categories().first().categoryName()
                            } ${detection.categories().first().score().toString().take(4)}",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}