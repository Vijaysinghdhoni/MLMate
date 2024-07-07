package com.vsdhoni5034.mlmate.presentation.navigation

import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.vsdhoni5034.mlmate.domain.model.Classification
import com.vsdhoni5034.mlmate.presentation.authentication.loading.LoadingScreen
import com.vsdhoni5034.mlmate.presentation.authentication.login.LoginScreen
import com.vsdhoni5034.mlmate.presentation.authentication.register.RegisterScreen
import com.vsdhoni5034.mlmate.presentation.landMarkRecoginzer.LandMarkScreen
import com.vsdhoni5034.mlmate.presentation.objectDetection.ObjectDetectionScreen
import com.vsdhoni5034.mlmate.presentation.optionsScreen.OptionsScreen


@Composable
fun Navigation(
    controller: LifecycleCameraController,
    classifications: List<Classification>,
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController, startDestination = Routes.AuthNav.route) {

        navigation(
            route = Routes.AuthNav.route,
            startDestination = Routes.LoadingScreen.route
        ) {


            composable(route = Routes.LoadingScreen.route) {

                LoadingScreen(
                    onNavigation = { route ->
                        navController.navigate(route) {
                            popUpTo(Routes.LoadingScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                )

            }

            composable(route = Routes.LoginScreen.route) {
                LoginScreen(onCreateAccountClick = {
                    navController.navigate(Routes.RegisterScreen.route) {
                        popUpTo(Routes.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                },
                    onSuccessLogin = {
                        navController.navigate(Routes.MlAppNav.route) {
                            popUpTo(Routes.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = Routes.RegisterScreen.route) {
                RegisterScreen(onLoginClick = {
                    navController.navigate(Routes.LoginScreen.route) {
                        popUpTo(Routes.RegisterScreen.route) {
                            inclusive = true
                        }
                    }
                },
                    onRegisterSuccess = {
                        navController.navigate(Routes.LoginScreen.route) {
                            popUpTo(Routes.RegisterScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        navigation(
            route = Routes.MlAppNav.route,
            startDestination = Routes.OptionsScreen.route
        ) {
            composable(route = Routes.OptionsScreen.route) {
                OptionsScreen(onObjectDetectionCLick = {
                    navController.navigate(Routes.ObjectDetectionScreen.route)
                },
                    onLandMarkClick = {
                        navController.navigate(Routes.LandMarkRecognizerScreen.route)
                    },
                    onNavigation = {
                        navController.navigate(Routes.AuthNav.route) {
                            popUpTo(Routes.OptionsScreen.route) {
                                inclusive = true
                            }
                        }
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


}