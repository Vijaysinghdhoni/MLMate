package com.vsdhoni5034.mlmate.presentation.authentication.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vsdhoni5034.mlmate.R
import com.vsdhoni5034.mlmate.presentation.authentication.register.components.RegisterTextField
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    onRegisterSuccess: () -> Unit
) {

    val registerScreenLocalState = registerViewModel.registerLocalState
    val registerState = registerViewModel.registerState
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        registerViewModel.registerUiEvent.collectLatest { event ->

            when (event) {

                is RegisterUiEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                is RegisterUiEvent.Navigate -> {
                    Toast.makeText(
                        context,
                        "Account Created Success! Login Now!!",
                        Toast.LENGTH_LONG
                    ).show()
                    onRegisterSuccess()
                }

            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                )
            }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                registerViewModel.onEvent(RegisterEvent.RegisterButtonClicked)
            }) {
                Icon(
                    modifier = Modifier.size(17.dp),
                    imageVector = Icons.Default.Create,
                    contentDescription = "Create"
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "create",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { padding ->

        Box(
            modifier =
            Modifier.padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card {
                    Column(
                        modifier =
                        Modifier
                            .padding(5.dp)
                    ) {

                        Text(
                            text = "Enter Your details", modifier =
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            RegisterTextField(
                                text = registerScreenLocalState.email,
                                labelText = "Email-Id",
                                onTextValueChange = {
                                    registerViewModel.onEvent(RegisterEvent.EmailEntered(it))
                                },
                                isError = registerScreenLocalState.isEmailError,
                                keyboardType = KeyboardType.Email,
                                error = registerScreenLocalState.emailError
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            RegisterTextField(
                                text = registerScreenLocalState.password,
                                labelText = "New Password",
                                onTextValueChange = {
                                    registerViewModel.onEvent(RegisterEvent.PasswordEntered(it))
                                },
                                isError = registerScreenLocalState.isPasswordError,
                                keyboardType = KeyboardType.Password,
                                error = registerScreenLocalState.passwordError,
                                isPasswordVisible = registerScreenLocalState.isPasswordVisible,
                                onPasswordToggleClicked = {
                                    registerViewModel.onEvent(RegisterEvent.PasswordToggleClicked)
                                }
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            RegisterTextField(
                                text = registerScreenLocalState.confirmPassword,
                                labelText = "Confirm Password",
                                onTextValueChange = {
                                    registerViewModel.onEvent(
                                        RegisterEvent.ConfirmPasswordEntered(
                                            it
                                        )
                                    )
                                },
                                isError = registerScreenLocalState.isConfirmPasswordError,
                                keyboardType = KeyboardType.Password,
                                error = registerScreenLocalState.confirmPasswordError,
                                isPasswordVisible = registerScreenLocalState.isConfirmPasswordVisible,
                                onPasswordToggleClicked = {
                                    registerViewModel.onEvent(RegisterEvent.ConfirmPasswordToggleClicked)
                                }
                            )

                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Already have an Account",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            TextButton(onClick = {
                                onLoginClick()
                            }) {
                                Text(
                                    text = "LogIn",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                    }
                }

            }

            if(registerState.loading){
                CircularProgressIndicator(
                    modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .width(60.dp)
                        .height(60.dp),
                    trackColor = Color.Black,
                    strokeWidth = 5.dp
                )
            }

        }

    }

}