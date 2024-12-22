@file:OptIn(ExperimentalLayoutApi::class)

package com.inspiredandroid.fitmaincharacter.screens.setup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inspiredandroid.fitmaincharacter.CardBackground
import com.inspiredandroid.fitmaincharacter.MediumTextStyle
import com.inspiredandroid.fitmaincharacter.SoftSand
import com.inspiredandroid.fitmaincharacter.SportFontFamily
import com.inspiredandroid.fitmaincharacter.TileBackground
import com.inspiredandroid.fitmaincharacter.components.LightButton
import com.inspiredandroid.fitmaincharacter.components.Selectable
import com.inspiredandroid.fitmaincharacter.components.TopBar
import com.inspiredandroid.fitmaincharacter.data.Workout
import com.inspiredandroid.fitmaincharacter.data.WorkoutUnit
import com.inspiredandroid.fitmaincharacter.screens.setup.SetupUiState.Page
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun SetupScreen(
    viewModel: SetupViewModel = androidx.lifecycle.viewmodel.compose.viewModel { SetupViewModel() },
    onSetupDone: (Workout) -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()

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
            Page.SelectExercises -> {
                SelectExercisesContent(
                    exercises = uiState.exercises,
                    onContinueClick = {
                        uiState.setPage(Page.SelectDifficulty)
                    },
                    toggleExercise = uiState.toggleExercise,
                )
            }

            Page.SelectDifficulty -> {
                SelectDifficultyContent(
                    difficulties = uiState.difficulties,
                    workout = uiState.workout,
                    onNavigateBack = {
                        uiState.setPage(Page.SelectExercises)
                    },
                    onContinueClick = {
                        onSetupDone(uiState.workout)
                        uiState.reset()
                    },
                    toggleDifficulty = uiState.toggleDifficulty,
                )
            }
        }
    }
}

@Composable
private fun SelectDifficultyContent(
    difficulties: List<Selectable>,
    workout: Workout,
    onNavigateBack: () -> Unit,
    onContinueClick: () -> Unit,
    toggleDifficulty: (Long) -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar(title = "Intensity", onNavigateBack = onNavigateBack)

        Spacer(Modifier.height(16.dp))

        FlowRow {
            difficulties.forEach { difficulty ->
                DifficultyButton(difficulty = difficulty, toggleDifficulty = toggleDifficulty)
            }
        }

        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            workout.rounds.forEachIndexed { index, round ->
                RoundPreview(index, round)
            }
        }

        Spacer(Modifier.height(16.dp))

        LightButton(
            onClick = onContinueClick,
            text = "Start",
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun DifficultyButton(difficulty: Selectable, toggleDifficulty: (Long) -> Unit) {
    Column(
        Modifier
            .padding(6.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(CardBackground).border(
                2.dp,
                if (difficulty.isSelected) SoftSand else Color.Transparent,
                shape = RoundedCornerShape(8.dp),
            )
            .clickable { toggleDifficulty(difficulty.id) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painterResource(difficulty.imageRes),
            null,
            Modifier.size(48.dp),
            tint = SoftSand,
        )
    }
}

@Composable
private fun RoundPreview(
    index: Int,
    round: Workout.Round,
) {
    Column(horizontalAlignment = Alignment.Start) {
        Spacer(Modifier.height(24.dp))
        Text(
            "Round ${index + 1}",
            style = MediumTextStyle(),
        )
        Spacer(Modifier.height(8.dp))

        FlowRow(Modifier.clip(RoundedCornerShape(4.dp)).background(TileBackground)) {
            round.exercises.forEach { exercise ->

                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${exercise.count} " + if (exercise.unit == WorkoutUnit.REPS) "X" else "sec",
                        style = MediumTextStyle(),
                    )
                    Icon(
                        painterResource(exercise.imageRes),
                        null,
                        Modifier.height(56.dp),
                        tint = SoftSand,
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectExercisesContent(
    exercises: List<Selectable>,
    toggleExercise: (Long) -> Unit,
    onContinueClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar(title = "Exercises")

        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Adaptive(128.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(exercises, key = { it.id }) { exercise ->
                ExerciseButton(
                    exercise = exercise,
                    onClick = {
                        toggleExercise(exercise.id)
                    },
                )
            }
        }

        LightButton(
            onClick = onContinueClick,
            text = "Continue",
            isVisible = exercises.any { it.isSelected },
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun ExerciseButton(exercise: Selectable, onClick: () -> Unit) {
    Column(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(CardBackground)
            .border(
                2.dp,
                if (exercise.isSelected) SoftSand else Color.Transparent,
                shape = RoundedCornerShape(8.dp),
            )
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painterResource(exercise.imageRes),
            null,
            Modifier.height(128.dp),
            tint = SoftSand,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            exercise.name.uppercase(),
            style = MaterialTheme.typography.h6,
            fontSize = 18.sp,
            color = SoftSand,
            fontFamily = SportFontFamily(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
