package com.oolong.wordle.ui.gameboard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oolong.wordle.domain.usecase.GetRandomWordUseCase
import com.oolong.wordle.ui.components.KeyboardButtonState
import com.oolong.wordle.ui.components.KeyboardData
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

    private fun getSecretWord() {
        viewModelScope.launch {
            val randomWord = getRandomWordUseCase.invoke()
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
        _uiState.update { it.copy(currentIndex = nextIndex) }
    }

    fun onOkButtonClicked() {
        val guessedWords = uiState.value.guessedWords
        val currentIndex = uiState.value.currentIndex

        if (guessedWords[currentIndex].length == 5) {
            increaseCurrentIndex(currentIndex)
            val keyboardState = updateKeyboardColors(
                secretWord = uiState.value.secretWord.toString(),
                guesses = uiState.value.guessedWords,
                keyboardData = uiState.value.keyboardState
            )
            _uiState.update { it.copy(keyboardState = keyboardState) }
        } else {
            // todo handle when guessed words list size
        }
    }

    fun onDeleteButtonClicked() {
        val guessedWords = uiState.value.guessedWords
        val currentIndex = uiState.value.currentIndex

        guessedWords[currentIndex] = guessedWords[currentIndex].dropLast(1)

        _uiState.update { it.copy(guessedWords = guessedWords) }
    }

    fun onLetterButtonClicked(letter: String) {
        val guessedWords = uiState.value.guessedWords
        val currentIndex = uiState.value.currentIndex

        if (guessedWords[currentIndex].length < 5) {
            guessedWords[currentIndex] += letter
        }

        _uiState.update { it.copy(guessedWords = guessedWords) }
    }

    fun onShowTipClicked() {
        _uiState.update { it.copy(shouldShowTip = !uiState.value.shouldShowTip) }
    }
}

data class GameBoardUiState(
    var currentIndex: Int = 0,
    val guessedWords: MutableList<String> = mutableStateListOf("", "", "", "", ""),
    val secretWord: String? = null,
    val keyboardState: KeyboardData = KeyboardData(),
    val shouldShowTip: Boolean = false
)

private fun updateKeyboardColors(
    secretWord: String, guesses: List<String>, keyboardData: KeyboardData
): KeyboardData {
    val buttonMap = (keyboardData.firstRow + keyboardData.secondRow + keyboardData.thirdRow)
        .associateBy({ it.letter }, { it.buttonState })
        .toMutableMap()

    guesses.forEach { guess ->
        val secretRemaining = secretWord.toMutableList()

        guess.forEachIndexed { index, letter ->
            if (index < secretWord.length && letter == secretWord[index]) {
                buttonMap[letter.toString()] = KeyboardButtonState.CORRECT
                secretRemaining[index] = '_'
            }
        }

        guess.forEachIndexed { index, letter ->
            if (buttonMap[letter.toString()] != KeyboardButtonState.CORRECT && letter in secretRemaining) {
                buttonMap[letter.toString()] = KeyboardButtonState.MISPLACED
                secretRemaining[secretRemaining.indexOf(letter)] = '_'
            } else {
                if (buttonMap[letter.toString()] != KeyboardButtonState.CORRECT) {
                    buttonMap[letter.toString()] = KeyboardButtonState.WRONG
                }
            }
        }
    }

    return KeyboardData(
        firstRow = keyboardData.firstRow.map { it.copy(buttonState = buttonMap[it.letter] ?: it.buttonState) },
        secondRow = keyboardData.secondRow.map { it.copy(buttonState = buttonMap[it.letter] ?: it.buttonState) },
        thirdRow = keyboardData.thirdRow.map { it.copy(buttonState = buttonMap[it.letter] ?: it.buttonState) },
    )
}