package com.inspiredandroid.fitmaincharacter.screens.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.inspiredandroid.fitmaincharacter.ScreenBackground
import com.inspiredandroid.fitmaincharacter.screens.app.AppUiState.Page
import com.inspiredandroid.fitmaincharacter.screens.setup.SetupScreen
import com.inspiredandroid.fitmaincharacter.screens.workout.WorkoutScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    viewModel: AppViewModel = viewModel { AppViewModel() },
    onStartWorkout: () -> Unit = {},
    onFinishWorkout: () -> Unit = {},
) {
    MaterialTheme {
        val uiState by viewModel.state.collectAsState()

        Box(
            Modifier.fillMaxSize().background(ScreenBackground).statusBarsPadding()
                .navigationBarsPadding(),
        ) {
            AnimatedContent(
                targetState = uiState.page,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(
                        animationSpec = tween(
                            300,
                        ),
                    )
                },
            ) { page ->
                when (page) {
                    Page.Setup -> {
                        SetupScreen(
                            onSetupDone = { workout ->
                                uiState.startWorkout(workout)
                                onStartWorkout()
                            },
                        )
                    }

                    Page.Workout -> {
                        WorkoutScreen(
                            workout = uiState.workout,
                            onFinish = {
                                uiState.setPage(Page.Setup)
                                onFinishWorkout()
                            },
                        )
                    }
                }
            }
        }
    }
}
