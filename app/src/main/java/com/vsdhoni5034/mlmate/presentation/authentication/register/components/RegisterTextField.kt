package com.vsdhoni5034.mlmate.presentation.authentication.register.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun RegisterTextField(
    modifier: Modifier = Modifier,
    text: String,
    labelText: String,
    onTextValueChange: (String) -> Unit,
    error: String? = null,
    isError: Boolean,
    onPasswordToggleClicked: (() -> Unit)? = null,
    isPasswordVisible: Boolean? = null,
    keyboardType: KeyboardType
) {

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onTextValueChange(it) },
        label = { Text(labelText) },
        trailingIcon = {
            isPasswordVisible?.let { isVisible ->
                val icon = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = {
                    if (onPasswordToggleClicked != null) {
                        onPasswordToggleClicked()
                    }
                }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
            }
        },
        visualTransformation = if (isPasswordVisible != null) {
            if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        isError = isError,
        supportingText = {
            error?.let {
                Text(text = it)
            }
        }
    )


}