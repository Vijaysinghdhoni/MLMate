package com.vsdhoni5034.mlmate.presentation.navigation

import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vsdhoni5034.mlmate.domain.model.Classification
import com.vsdhoni5034.mlmate.presentation.landMarkRecoginzer.LandMarkScreen
import com.vsdhoni5034.mlmate.presentation.objectDetection.ObjectDetectionScreen
import com.vsdhoni5034.mlmate.presentation.optionsScreen.OptionsScreen

@Composable
fun Navigation(
    controller: LifecycleCameraController,
    classifications: List<Classification>,
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController, startDestination = Routes.OptionsScreen.route) {

        composable(route = Routes.OptionsScreen.route) {
            OptionsScreen(onObjectDetectionCLick = {
                navController.navigate(Routes.ObjectDetectionScreen.route)
            },
                onLandMarkClick = {
                    navController.navigate(Routes.LandMarkRecognizerScreen.route)
                }
            )
        }

        composable(route = Routes.ObjectDetectionScreen.route) {
            ObjectDetectionScreen()
        }

        composable(route = Routes.LandMarkRecognizerScreen.route) {
            LandMarkScreen(controller = controller, classifications = classifications)
        }

    }


}