package com.oolong.wordle.ui.endgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EndGameScreen(
    modifier: Modifier = Modifier,
    onMainMenuClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(onClick = { onMainMenuClick() }) {
            Text(text = "Go main menu")
        }
    }
}