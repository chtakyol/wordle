package com.oolong.wordle.ui.gameboard

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oolong.wordle.ui.components.GuessMatrix
import com.oolong.wordle.ui.components.KeyboardComponent

@Composable
fun GameBoardScreen(
    viewModel: GameBoardViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    val currentIndex = uiState.value.currentIndex
    val guessedWords = uiState.value.guessedWords

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiState.value.secretWord?.let { secretWord ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = secretWord)
                Button(
                    onClick = {
                    viewModel.getSecretWord()
                    }
                ) {
                    Text(text = "Get new word")
                }
            }

            GuessMatrix(guessedWords = guessedWords, currentIndex = currentIndex, secretWord = secretWord)
            KeyboardComponent(
                keyboardData = uiState.value.keyboardState,
                onOKButtonClicked = {
                    if (guessedWords[currentIndex].length == 5) {
                        viewModel.increaseCurrentIndex(currentIndex)
                        viewModel.okButtonClicked()
                    } else {
                        Log.d("asd", "Wrong size amk")
                    }
                },
                onDeleteButtonClicked = {
                    guessedWords[currentIndex] = guessedWords[currentIndex].dropLast(1)
                },
                onLetterButtonClicked = {
                    if (guessedWords[currentIndex].length < 5) {
                        guessedWords[currentIndex] += it
                    }
                }
            )
        }
    }
}