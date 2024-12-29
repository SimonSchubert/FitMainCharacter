@file:OptIn(ExperimentalResourceApi::class)

package com.inspiredandroid.fitmaincharacter

import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import fitmaincharacter.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

actual var player: GadulkaPlayer? = null

actual fun playAudio(resString: String) {
    player?.play(Res.getUri(resString))
}
