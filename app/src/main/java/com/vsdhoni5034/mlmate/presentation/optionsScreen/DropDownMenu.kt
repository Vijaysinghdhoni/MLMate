package com.vsdhoni5034.mlmate.presentation.optionsScreen

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onLogoutClick: () -> Unit,
    onDismiss: () -> Unit
) {

    DropdownMenu(expanded = expanded,
        onDismissRequest = {
            onDismiss()
        })
    {

        DropdownMenuItem(text = {
            Text(text = "Logout")
        },
            onClick = { onLogoutClick() })
    }


}