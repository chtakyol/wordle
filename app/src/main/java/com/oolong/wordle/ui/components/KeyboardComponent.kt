package com.oolong.wordle.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val BUTTON_HEIGHTS = 54
const val LETTER_BUTTON_WIDTH = 32
const val FUNCTIONAL_BUTTON_WIDTH = 48
const val CORNER_RADIUS = 10

data class KeyboardButtonData(
    val letter: String,
    val buttonState: KeyboardButtonState = KeyboardButtonState.START
)

data class KeyboardData(
    val firstRow: List<KeyboardButtonData> = listOf(
        KeyboardButtonData("q"),
        KeyboardButtonData("e"),
        KeyboardButtonData("r"),
        KeyboardButtonData("t"),
        KeyboardButtonData("w"),
        KeyboardButtonData("y"),
        KeyboardButtonData("u"),
        KeyboardButtonData("i"),
        KeyboardButtonData("o"),
        KeyboardButtonData("p")
    ),
    val secondRow: List<KeyboardButtonData> = listOf(
        KeyboardButtonData("a"),
        KeyboardButtonData("s"),
        KeyboardButtonData("d"),
        KeyboardButtonData("f"),
        KeyboardButtonData("g"),
        KeyboardButtonData("h"),
        KeyboardButtonData("j"),
        KeyboardButtonData("k"),
        KeyboardButtonData("l")
    ),
    val thirdRow: List<KeyboardButtonData> = listOf(
        KeyboardButtonData("z"),
        KeyboardButtonData("x"),
        KeyboardButtonData("c"),
        KeyboardButtonData("v"),
        KeyboardButtonData("b"),
        KeyboardButtonData("n"),
        KeyboardButtonData("m")
    ),
)

@Composable
fun KeyboardComponent(
    modifier:Modifier = Modifier,
    keyboardData: KeyboardData = KeyboardData(),
    onOKButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: () -> Unit = {},
    onLetterButtonClicked: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            keyboardData.firstRow.forEach { keyboardButtonData ->
                KeyboardButton(
                    keyboardButtonData = keyboardButtonData,
                    onClick = onLetterButtonClicked
                )
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth(.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            keyboardData.secondRow.forEach { keyboardButtonData ->
                KeyboardButton(
                    keyboardButtonData = keyboardButtonData,
                    onClick = onLetterButtonClicked
                )
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DeleteButton(onClick = onDeleteButtonClicked)
            keyboardData.thirdRow.forEach { keyboardButtonData ->
                KeyboardButton(
                    keyboardButtonData = keyboardButtonData,
                    onClick = onLetterButtonClicked
                )
            }
            OKButton(onClick = onOKButtonClicked)
        }
    }
}

@Composable
@Preview
fun PreviewKeyboardLayout() {
    KeyboardComponent()
}

enum class KeyboardButtonState {
    START, CORRECT, MISPLACED, WRONG
}

@Composable
fun KeyboardButton(
    keyboardButtonData: KeyboardButtonData,
    onClick: (String) -> Unit
) {
    val gray = Color(	0xFF787c7f)
    val yellow = Color(	0xFFc8b653)
    val green = Color(	0xFF6ca965)

    val buttonColors = when(keyboardButtonData.buttonState) {
        KeyboardButtonState.START -> {
            ButtonDefaults.textButtonColors(containerColor = Color(0xFFd4d6da), contentColor = Color.Black)
        }
        KeyboardButtonState.CORRECT -> {
            ButtonDefaults.textButtonColors(containerColor = green, contentColor = Color.White)
        }
        KeyboardButtonState.MISPLACED -> {
            ButtonDefaults.textButtonColors(containerColor = yellow, contentColor = Color.White)
        }
        KeyboardButtonState.WRONG -> {
            ButtonDefaults.textButtonColors(containerColor = gray, contentColor = Color.White)
        }
    }

    TextButton(
        modifier = Modifier
            .size(width = LETTER_BUTTON_WIDTH.dp, height = BUTTON_HEIGHTS.dp),
        colors = buttonColors,
        shape = RoundedCornerShape(CORNER_RADIUS.dp),
        onClick = {
            onClick(keyboardButtonData.letter)
        }
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = keyboardButtonData.letter.uppercase(),
            fontSize = 24.sp
        )
    }
}

@Composable
fun OKButton(
    onClick: () -> Unit
) {
    val buttonColors = IconButtonDefaults.iconButtonColors(
        containerColor = Color(	0xFFd4d6da),
        contentColor = Color.Black
    )
    IconButton(
        modifier = Modifier
            .size(width = FUNCTIONAL_BUTTON_WIDTH.dp, height = BUTTON_HEIGHTS.dp),
        colors = buttonColors,
        onClick = {
            onClick()
        }
    ) {
        Icon(
            Icons.Rounded.Lock,
            tint = Color.LightGray,
            contentDescription = ""
        )

    }
}

@Composable
fun DeleteButton(
    onClick: () -> Unit
) {
    val buttonColors = IconButtonDefaults.iconButtonColors(
        containerColor = Color(	0xFFd4d6da),
        contentColor = Color.Black
    )
    IconButton(
        modifier = Modifier
            .size(width = FUNCTIONAL_BUTTON_WIDTH.dp, height = BUTTON_HEIGHTS.dp),
        colors = buttonColors,
        onClick = {
            onClick()
        }
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.ArrowBack,
            tint = Color.LightGray,
            contentDescription = ""
        )
    }
}

@Composable
@Preview
fun PreviewKeyboardButton() {
    KeyboardButton(
        keyboardButtonData = KeyboardButtonData(
            letter = "a",
            buttonState = KeyboardButtonState.MISPLACED,
        )
    ) {}
}

@Composable
@Preview
fun PreviewOKButton() {
    OKButton {

    }
}

@Composable
@Preview
fun PreviewDeleteButton() {
    DeleteButton {

    }
}