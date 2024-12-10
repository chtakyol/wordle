package com.oolong.wordle.ui.gameboard

import android.util.Log
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolong.wordle.domain.usecase.GetRandomWordUseCase
import com.oolong.wordle.ui.components.KeyboardButtonState
import com.oolong.wordle.ui.components.KeyboardData
import com.oolong.wordle.ui.components.LetterBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameBoardViewModel @Inject constructor(
    private val getRandomWordUseCase: GetRandomWordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameBoardUiState())
    val uiState: StateFlow<GameBoardUiState> = _uiState.asStateFlow()

    init {
        getSecretWord()
    }

    fun getSecretWord() {
        viewModelScope.launch {
            val randomWord = getRandomWordUseCase.invoke()
            Log.d("asd", "Secret word: $randomWord")
            _uiState.update {
                it.copy(
                    currentIndex = 0,
                    guessedWords = mutableStateListOf("", "", "", "", ""),
                    secretWord = randomWord?.word
                )
            }
        }
    }

    fun increaseCurrentIndex(currentIndex: Int) {
        val nextIndex = currentIndex + 1
        _uiState.update {
            it.copy(currentIndex = nextIndex)
        }
    }

    fun okButtonClicked() {
        val keyboardState = updateKeyboardColors(
            secretWord = uiState.value.secretWord.toString(),
            guesses = uiState.value.guessedWords,
            keyboardData = uiState.value.keyboardState
        )
        _uiState.update {
            it.copy(
                keyboardState = keyboardState
            )
        }
    }
}

data class GameBoardUiState(
    var currentIndex: Int = 0,
    val guessedWords: MutableList<String> = mutableStateListOf("", "", "", "", ""),
    val secretWord: String? = null,
    val keyboardState: KeyboardData = KeyboardData()
)

private fun updateKeyboardColors(
    secretWord: String, guesses: List<String>, keyboardData: KeyboardData
): KeyboardData {
    // Flatten the keyboard data to a mutable map for easier updates
    val buttonMap = (keyboardData.firstRow + keyboardData.secondRow + keyboardData.thirdRow)
        .associateBy({ it.letter }, { it.buttonState })
        .toMutableMap()

    // Process each guessed word
    guesses.forEach { guess ->
        // Keep track of remaining unmatched letters in the secret word
        val secretRemaining = secretWord.toMutableList()

        // First pass: Check for correct positions
        guess.forEachIndexed { index, letter ->
            if (index < secretWord.length && letter == secretWord[index]) {
                buttonMap[letter.toString()] = KeyboardButtonState.CORRECT
                secretRemaining[index] = '_' // Mark as used
            }
        }

        // Second pass: Check for misplaced letters
        guess.forEachIndexed { index, letter ->
            if (buttonMap[letter.toString()] != KeyboardButtonState.CORRECT && letter in secretRemaining) {
                buttonMap[letter.toString()] = KeyboardButtonState.MISPLACED
                secretRemaining[secretRemaining.indexOf(letter)] = '_' // Mark as used
            } else {
                if (buttonMap[letter.toString()] != KeyboardButtonState.CORRECT) {
                    buttonMap[letter.toString()] = KeyboardButtonState.WRONG
                }
            }
        }
    }

    // Update keyboard data with the new states
    return KeyboardData(
        firstRow = keyboardData.firstRow.map { it.copy(buttonState = buttonMap[it.letter] ?: it.buttonState) },
        secondRow = keyboardData.secondRow.map { it.copy(buttonState = buttonMap[it.letter] ?: it.buttonState) },
        thirdRow = keyboardData.thirdRow.map { it.copy(buttonState = buttonMap[it.letter] ?: it.buttonState) },
    )
}