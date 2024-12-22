@file:Suppress("ktlint:standard:filename")

package com.inspiredandroid.fitmaincharacter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import com.inspiredandroid.fitmaincharacter.screens.app.App
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import sport.composeapp.generated.resources.Res
import sport.composeapp.generated.resources.exercise_stretching

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        var showSplash by remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            while (true) {
                delay(1000)
                showSplash = false
            }
        }
        if (showSplash) {
            WebSplash()
        } else {
            App()
        }
    }
}

@Composable
private fun WebSplash() {
    Box(
        Modifier.fillMaxSize().background(ScreenBackground),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painterResource(Res.drawable.exercise_stretching),
            null,
            Modifier.height(160.dp),
            tint = SoftSand,
        )
        Text(
            text = " ", // dirty fix for font loading issue on web
            fontFamily = SportFontFamily(),
        )
    }
}
