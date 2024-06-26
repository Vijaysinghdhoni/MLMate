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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vsdhoni5034.mlmate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsScreen(
    modifier: Modifier = Modifier,
    onObjectDetectionCLick: () -> Unit,
    onLandMarkClick: () -> Unit
) {

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
                    IconButton(onClick = { }) {
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
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
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