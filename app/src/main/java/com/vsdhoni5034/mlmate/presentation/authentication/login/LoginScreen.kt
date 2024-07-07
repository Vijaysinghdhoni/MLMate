package com.vsdhoni5034.mlmate.presentation.authentication.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import com.vsdhoni5034.mlmate.presentation.authentication.login.components.LoginTextField
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onCreateAccountClick: () -> Unit,
    onSuccessLogin: () -> Unit
) {

    val loginLocalState = loginViewModel.loginLocalState
    val loginScreenState = loginViewModel.loginScreenState
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        loginViewModel.loginUiEvent.collectLatest { event ->

            when (event) {

                is LoginUiEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                is LoginUiEvent.NavigationEvent -> {
                    Toast.makeText(context, "You are success login !", Toast.LENGTH_LONG).show()
                    onSuccessLogin()
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
                loginViewModel.onEvent(LoginEvent.loginButtonClicked)
            }) {
                Icon(
                    modifier = Modifier.size(17.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Create"
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { padding ->

        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(modifier = Modifier.padding(padding)) {
                    Column(
                        modifier =
                        Modifier
                            .padding(4.dp)
                    ) {

                        Text(
                            text = "Enter Your details", modifier =
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            LoginTextField(
                                text = loginLocalState.email,
                                labelText = "Email-Id",
                                onTextValueChange = {
                                    loginViewModel.onEvent(LoginEvent.EmailEntered(it))
                                },
                                isError = loginLocalState.isEmailError,
                                keyboardType = KeyboardType.Email,
                                error = loginLocalState.emailError
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            LoginTextField(
                                text = loginLocalState.password,
                                labelText = "Password",
                                onTextValueChange = {
                                    loginViewModel.onEvent(LoginEvent.PasswordEntered(it))
                                },
                                isError = loginLocalState.isPasswordError,
                                keyboardType = KeyboardType.Password,
                                error = loginLocalState.passwordError,
                                isPasswordVisible = loginLocalState.isPasswordVisible,
                                onPasswordToggleClicked = {
                                    loginViewModel.onEvent(LoginEvent.PasswordToggleClicked)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Don't have an Account",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            TextButton(onClick = {
                                onCreateAccountClick()
                            }) {
                                Text(
                                    text = "Create",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                    }
                }

            }

            if (loginScreenState.isLoading) {
                CircularProgressIndicator(
                    modifier =
                    Modifier
                        .align(Alignment.Center)
                        .width(60.dp)
                        .height(60.dp),
                    trackColor = Color.Black,
                    strokeWidth = 5.dp
                )
            }

        }

    }

}