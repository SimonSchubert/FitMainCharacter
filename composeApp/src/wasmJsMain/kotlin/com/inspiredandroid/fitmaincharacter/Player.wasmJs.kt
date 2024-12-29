@file:OptIn(
    ExperimentalResourceApi::class,
)

package com.inspiredandroid.fitmaincharacter

import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import fitmaincharacter.composeapp.generated.resources.Res
import kotlinx.browser.document
import org.jetbrains.compose.resources.ExperimentalResourceApi

actual var player: GadulkaPlayer? = null
actual fun playAudio(resString: String) {
    if (player == null) {
        player = GadulkaPlayer("fit_audio_player")
    }
    document.getElementById("fit_audio_player")?.remove()
    player?.play(Res.getUri(resString))
}
