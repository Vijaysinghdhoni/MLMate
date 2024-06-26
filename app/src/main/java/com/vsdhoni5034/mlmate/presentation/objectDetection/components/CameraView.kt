package com.vsdhoni5034.mlmate.presentation.objectDetection.components

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.vsdhoni5034.mlmate.presentation.objectDetection.ObjectDetectionViewModel
import com.vsdhoni5034.mlmate.data.ObjectDetectorHelper
import com.vsdhoni5034.mlmate.data.ObjectDetectorListenerImpl
import com.vsdhoni5034.mlmate.presentation.objectDetection.getFittedBoxSize
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    setInferenceTime: (newInferenceTime: Int) -> Unit,
    objectDetectionViewModel: ObjectDetectionViewModel = viewModel()
) {

    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    LaunchedEffect(key1 = Unit) {
        if (!cameraPermissionState.hasPermission) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (!cameraPermissionState.hasPermission) {
        Text(text = "No camera permission is allowed.")
        return
    }


    val results = objectDetectionViewModel.results

    val frameWidth = objectDetectionViewModel.frameWidth

    val frameHeight = objectDetectionViewModel.frameHeight

    val active = objectDetectionViewModel.active

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    DisposableEffect(Unit) {
        onDispose {
            objectDetectionViewModel.updateActive(false)
            cameraProviderFuture.get().unbindAll()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {

        val cameraPreviewSize = getFittedBoxSize(
            containerSize = Size(
                width = this.maxWidth.value,
                height = this.maxHeight.value,
            ),
            boxSize = Size(
                width = frameWidth.toFloat(),
                height = frameHeight.toFloat()
            )
        )

        // Now that we have the exact UI dimensions of the camera preview, we apply them to a Box
        // composable, which will contain the camera preview and the results overlay on top of it,
        // both having the exact same UI dimensions
        Box(
            Modifier
                .width(cameraPreviewSize.width.dp)
                .height(cameraPreviewSize.height.dp),
        ) {
            // We're using CameraX to use the phone's camera, and since it doesn't have a prebuilt
            // composable in Jetpack Compose, we use AndroidView to implement it
            AndroidView(
                factory = { ctx ->
                    // We start by instantiating the camera preview view to be displayed
                    val previewView = PreviewView(ctx)
                    val executor = ContextCompat.getMainExecutor(ctx)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()

                        // We set a surface for the camera input feed to be displayed in, which is
                        // in the camera preview view we just instantiated
                        val preview = Preview.Builder().build().also {
                            it.surfaceProvider = previewView.surfaceProvider
                        }

                        // We specify what phone camera to use. In our case it's the back camera
                        val cameraSelector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()

                        // We instantiate an image analyser to apply some transformations on the
                        // input frame before feeding it to the object detector
                        val imageAnalyzer =
                            ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                                .build()

                        // Now we're ready to apply object detection. For a better performance, we
                        // execute the object detection process in a new thread.
                        val backgroundExecutor = Executors.newSingleThreadExecutor()

                        backgroundExecutor.execute {

                            // To apply object detection, we use our ObjectDetectorHelper class,
                            // which abstracts away the specifics of using MediaPipe  for object
                            // detection from the UI elements of the app
                            val objectDetectorHelper =
                                ObjectDetectorHelper(
                                    context = ctx,
                                    // Since we're detecting objects in a live camera feed, we need
                                    // to have a way to listen for the results
                                    objectDetectorListener = ObjectDetectorListenerImpl(
                                        onErrorCallback = { _, _ -> },
                                        onResultsCallback = {
                                            // On receiving results, we now have the exact camera
                                            // frame dimensions, so we set them here
                                            objectDetectionViewModel.updateFrameHeight(it.inputImageHeight)
                                            objectDetectionViewModel.updateFrameWidth(it.inputImageWidth)

                                            // Then we check if the camera view is still active,
                                            // if so, we set the state of the results and
                                            // inference time.
                                            if (active) {
                                                objectDetectionViewModel.updateResults(it.results.first())
                                                setInferenceTime(it.inferenceTime.toInt())
                                            }
                                        }
                                    ),
                                    runningMode = RunningMode.LIVE_STREAM
                                )

                            // Now that we have our ObjectDetectorHelper instance, we set is as an
                            // analyzer and start detecting objects from the camera live stream
                            imageAnalyzer.setAnalyzer(
                                backgroundExecutor,
                                objectDetectorHelper::detectLiveStreamFame
                            )
                        }

                        // We close any currently open camera just in case, then open up
                        // our own to be display the live camera feed
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            imageAnalyzer,
                            preview
                        )
                    }, executor)
                    // We return our preview view from the AndroidView factory to display it
                    previewView
                },
                modifier = Modifier.fillMaxSize(),
            )
            // Finally, we check for current results, if there's any, we display the results overlay
            results?.let {
                ResultOverlay(
                    result = it,
                    frameWidth = frameWidth,
                    frameHeight = frameHeight
                )
            }
        }
    }

}