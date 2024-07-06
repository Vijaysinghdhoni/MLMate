package com.vsdhoni5034.mlmate.presentation.authentication.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vsdhoni5034.mlmate.presentation.navigation.Routes
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    loadingScreenViewModel: LoadingScreenViewModel = hiltViewModel(),
    onNavigation: (String) -> Unit
) {


    LaunchedEffect(key1 = true) {

        loadingScreenViewModel.isUserLogin.collectLatest {

            if (it != null && it == true) {
                onNavigation(Routes.MlAppNav.route)
            } else {
                onNavigation(Routes.LoginScreen.route)
            }
        }
    }



    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(64.dp),
            color = Color.Black,
            strokeWidth = 4.dp,
            trackColor = Color.Gray
        )
    }


}