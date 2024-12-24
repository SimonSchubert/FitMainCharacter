package com.inspiredandroid.fitmaincharacter

import androidx.compose.ui.window.ComposeUIViewController
import com.inspiredandroid.fitmaincharacter.screens.app.App
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer

fun MainViewController() = ComposeUIViewController {
    val player = GadulkaPlayer()
    player.play(url = "...")
    player.stop()
    player.release()
    App()
}
