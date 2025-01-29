package com.inspiredandroid.fitmaincharacter.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle

@Composable
fun AnimatedNumber(
    number: Int,
    style: TextStyle,
) {
    val digits = remember(number) {
        number.toString().map { char ->
            Digit(char, number)
        }
    }

    Row {
        digits.forEach { digit ->
            AnimatedContent(
                targetState = digit,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically { -it } togetherWith slideOutVertically { it }
                    } else {
                        slideInVertically { it } togetherWith slideOutVertically { -it }
                    }
                },
            ) { targetDigit ->
                Text(
                    text = targetDigit.digitChar.toString(),
                    style = style.copy(fontFeatureSettings = "tnum"),
                )
            }
        }
    }
}

private data class Digit(val digitChar: Char, val fullNumber: Int) {
    override fun equals(other: Any?): Boolean = when (other) {
        is Digit -> digitChar == other.digitChar
        else -> false
    }
}

private operator fun Digit.compareTo(other: Digit): Int = fullNumber.compareTo(other.fullNumber)
