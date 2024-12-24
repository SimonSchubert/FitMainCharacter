@file:OptIn(ExperimentalResourceApi::class)

package com.inspiredandroid.fitmaincharacter

import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import org.jetbrains.compose.resources.ExperimentalResourceApi
import sport.composeapp.generated.resources.Res

actual var player: GadulkaPlayer? = null
actual fun playAudio(resString: String) {
    player?.play(Res.getUri(resString))
}
