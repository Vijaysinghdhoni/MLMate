package com.vsdhoni5034.mlmate.presentation.optionsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vsdhoni5034.mlmate.R
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsScreen(
    modifier: Modifier = Modifier,
    onObjectDetectionCLick: () -> Unit,
    onLandMarkClick: () -> Unit,
    optionsViewModel: OptionsViewModel = hiltViewModel(),
    onNavigation: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        optionsViewModel.optionsUiEvent.collectLatest {
            if (it is OptionsUiEvent.NavigationEvent) {
                onNavigation()
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name), style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                )
            },
                actions = {
                    IconButton(onClick = {
                        expanded = !expanded
                    }) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                    }
                }
            )
        }
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {

            DropDownMenu(
                expanded = expanded,
                onLogoutClick = {
                    optionsViewModel.onLogoutClick()
                },
                onDismiss = {
                    expanded = false
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Text(
                    text =
                    "Explore Object Detection and Tracking",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onObjectDetectionCLick()
                    },
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(text = "Explore")
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text =
                    "Explore Asian Landmarks Recognizing",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Default
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        onLandMarkClick()
                    },
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(text = "Explore")
                }


            }

        }

    }


}