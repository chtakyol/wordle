package com.oolong.wordle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GuessMatrix(
    guessedWords: List<String>,
    currentIndex: Int,
    secretWord: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        guessedWords.forEachIndexed { index, element ->
            LetterRow(
                word = element,
                isEditMode = index >= currentIndex,
                secretWord = secretWord
            )
        }
    }
}

@Preview
@Composable
private fun PreviewGuessMatrix() {
    GuessMatrix(
        guessedWords = listOf("asdpp", "", "", "", ""),
        currentIndex = 1,
        secretWord = "apple"
    )
}

@Composable
fun LetterRow(word: String, isEditMode:Boolean = true, secretWord: String) {
    require(word.length <= 5) { "Input string cannot be longer than 5 characters" }

    val charArray = CharArray(5)
    word.toCharArray(charArray, 0)
    for (i in word.length until 5) {
        charArray[i] = ' '
    }
    val states = mutableListOf<LetterBoxState>()
    if (isEditMode) {
        for (i in 0 until 5) {
            if (i < word.length) {
                states.add(LetterBoxState.WRITTEN)
            } else {
                states.add(LetterBoxState.EMPTY)
            }
        }
    } else {
        states.addAll(provideFeedback(guessedWord = word, secretWord = secretWord))
    }


    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in charArray.indices) {
            LetterBox(letter = charArray[i], state = states[i])
        }
    }
}

@Preview
@Composable
private fun PreviewLetterRow() {
    LetterRow(word = "", secretWord = "apple", isEditMode = true)
}

@Composable
fun LetterBox(letter: Char, state: LetterBoxState) {

    val gray = Color(	0xFF787c7f)
    val yellow = Color(	0xFFc8b653)
    val green = Color(	0xFF6ca965)

    val letterBoxColors = when (state) {
        LetterBoxState.EMPTY -> LetterBoxColors(outline = gray, background = Color.White, textColor = Color.Black)
        LetterBoxState.WRITTEN -> LetterBoxColors(outline = gray, background = Color.White, textColor = Color.Black)
        LetterBoxState.CORRECT -> LetterBoxColors(outline = Color.White, background = green, textColor = Color.White)
        LetterBoxState.WRONG -> LetterBoxColors(outline = Color.White, background = gray, textColor = Color.White)
        LetterBoxState.MISPLACED -> LetterBoxColors(outline = Color.White, background = yellow, textColor = Color.White)
    }
    
    Box(
        modifier = Modifier
            .size(60.dp, 75.dp)
            .background(letterBoxColors.background)
            .border(width = 2.dp, letterBoxColors.outline),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.uppercase(),
            color = letterBoxColors.textColor,
            fontSize = 48.sp
        )
    }
}

@Preview
@Composable
private fun PreviewLetterBoxEmpty() {
    LetterBox(letter = ' ', state = LetterBoxState.EMPTY)
}

@Preview
@Composable
private fun PreviewLetterBoxCorrect() {
    LetterBox(letter = 'A', state = LetterBoxState.EMPTY)
}

data class LetterBoxColors(
    val outline: Color,
    val background: Color,
    val textColor: Color
)

enum class LetterBoxState {
    EMPTY, WRITTEN, CORRECT, WRONG, MISPLACED
}

fun provideFeedback(secretWord: String, guessedWord: String): List<LetterBoxState> {
    val feedback = mutableListOf<LetterBoxState>()

    guessedWord.forEachIndexed { index, guessedLetter ->
        if (secretWord.getOrNull(index) == guessedLetter) {
            feedback.add(LetterBoxState.CORRECT)
        } else if (secretWord.contains(guessedLetter)) {
            feedback.add(LetterBoxState.MISPLACED)
        } else {
            feedback.add(LetterBoxState.WRONG)
        }
    }

    // Fill the remaining positions with wrong feedback
    repeat(secretWord.length - (guessedWord.length)) {
        feedback.add(LetterBoxState.WRONG)
    }
    return feedback
}
