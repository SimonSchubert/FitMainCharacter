@file:Suppress("ktlint:standard:filename")

package com.inspiredandroid.fitmaincharacter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.inspiredandroid.fitmaincharacter.screens.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Fit Main Character",
    ) {
        App()
    }
}
