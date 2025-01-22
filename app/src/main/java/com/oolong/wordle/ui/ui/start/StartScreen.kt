package com.oolong.wordle.ui.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oolong.wordle.BuildConfig

@Composable
fun StartScreen(
    onDailyWordleClick: () -> Unit
) {
    StartScreenContent(
        onDailyWordleClick = onDailyWordleClick
    )
}

@Composable
fun StartScreenContent(
    onDailyWordleClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        Text(text = "Logo")
        Text(text = "Build type: ${BuildConfig.BUILD_TYPE}")

        Button(
            onClick = { onDailyWordleClick() }
        ) {
            Text(text = "Daily wordle")
        }
        Text(text = "New Wordle available or next wordle in 12 hour")

        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Infinitive wordle")
        }
        Text(text = "Max streak 12! current 5")
    }
}

@Preview
@Composable
private fun PreviewStartScreen() {
    StartScreen(onDailyWordleClick = {})
}