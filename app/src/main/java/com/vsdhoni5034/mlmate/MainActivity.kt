package com.vsdhoni5034.mlmate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vsdhoni5034.mlmate.data.TfLiteLandMarkClassifier
import com.vsdhoni5034.mlmate.presentation.landMarkRecoginzer.LandMarkImageAnalyzer
import com.vsdhoni5034.mlmate.presentation.landMarkRecoginzer.LandMarkViewModel
import com.vsdhoni5034.mlmate.presentation.navigation.Navigation
import com.vsdhoni5034.mlmate.ui.theme.MLMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), 0
            )
        }
        setContent {
            MLMateTheme {
                val landmarkViewModel = viewModel<LandMarkViewModel>()
                val classification  = landmarkViewModel.classifications
                val analyzer = remember {
                    LandMarkImageAnalyzer(
                        classifier = TfLiteLandMarkClassifier(
                            context = applicationContext
                        ),
                        onResult = {
                            landmarkViewModel.updateClassifications(it)
                        }
                    )
                }

                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                        setImageAnalysisAnalyzer(
                            ContextCompat.getMainExecutor(applicationContext),
                            analyzer
                        )
                    }
                }

                Navigation(
                    controller = controller, classifications = classification
                )
            }
        }
    }

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

//koltin 2.0
//advance permission handling
//improve ui of options screen , landmark , object detection
//gallery import photos object detction not working
//login & signUp feature
//di
//splash screen
//push to github with readme file and video in it

