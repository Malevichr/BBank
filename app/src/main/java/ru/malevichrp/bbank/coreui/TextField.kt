package ru.malevichrp.bbank.coreui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    state: TextFieldState,
    label: String
) {
    OutlinedTextField(
        state,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        label = {
            Text(label)
        }
    )
}