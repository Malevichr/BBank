package ru.malevichrp.bbank.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


//todo replace with not fake
@Composable
fun MainFake() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            "Here is nothing yet",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}