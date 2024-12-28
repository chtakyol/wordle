package com.oolong.wordle.ui.gameboard


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oolong.wordle.R
import com.oolong.wordle.ui.components.GuessMatrix
import com.oolong.wordle.ui.components.KeyboardComponent
import com.oolong.wordle.ui.components.KeyboardData

@Composable
fun GameBoardScreen(
    viewModel: GameBoardViewModel = hiltViewModel(),
    onMenuButtonClicked: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    GameBoardContent(
        uiState = uiState.value,
        onOkClicked = {
            viewModel.onOkButtonClicked()
        },
        onDeleteClicked = {
            viewModel.onDeleteButtonClicked()
        },
        onLetterButtonClicked = { letter ->
            viewModel.onLetterButtonClicked(letter)
        },
        onShowTipClicked = {
            viewModel.onShowTipClicked()
        },
        onMenuButtonClicked = {
            onMenuButtonClicked()
        }
    )
}

@Composable
fun GameBoardContent(
    uiState: GameBoardUiState,
    onOkClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onLetterButtonClicked: (String) -> Unit,
    onShowTipClicked: () -> Unit,
    onMenuButtonClicked: () -> Unit
) {
    val currentIndex = uiState.currentIndex
    val guessedWords = uiState.guessedWords

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiState.secretWord?.let { secretWord ->
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier
                        .clickable { onMenuButtonClicked() },
                    text = "Menu"
                )
            }

            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = ""
            )

            GuessMatrix(
                guessedWords = guessedWords,
                currentIndex = currentIndex,
                secretWord = secretWord
            )

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            onShowTipClicked()
                        },
                    text = if (uiState.shouldShowTip) secretWord else "Show tip"
                )

                KeyboardComponent(
                    keyboardData = uiState.keyboardState,
                    onOKButtonClicked = {
                        onOkClicked()
                    },
                    onDeleteButtonClicked = {
                        onDeleteClicked()
                    },
                    onLetterButtonClicked = { letter ->
                        onLetterButtonClicked(letter)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGameBoardContent() {
    val guessedWord = remember { mutableStateListOf("apple", "", "", "", "") }
    val uiState = GameBoardUiState(
        currentIndex = 0,
        guessedWords = guessedWord,
        secretWord = "apple",
        keyboardState = KeyboardData()
    )

    GameBoardContent(
        uiState = uiState,
        onOkClicked = { },
        onDeleteClicked = { },
        onLetterButtonClicked = { },
        onShowTipClicked = { },
        onMenuButtonClicked = { }
    )
}