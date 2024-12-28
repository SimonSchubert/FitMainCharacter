@file:OptIn(ExperimentalLayoutApi::class)

package com.inspiredandroid.fitmaincharacter.screens.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.inspiredandroid.fitmaincharacter.CardBackground
import com.inspiredandroid.fitmaincharacter.MediumTextStyle
import com.inspiredandroid.fitmaincharacter.SoftSand
import com.inspiredandroid.fitmaincharacter.TileBackground
import com.inspiredandroid.fitmaincharacter.components.LightButton
import com.inspiredandroid.fitmaincharacter.components.Selectable
import com.inspiredandroid.fitmaincharacter.components.TopBar
import com.inspiredandroid.fitmaincharacter.data.Workout
import com.inspiredandroid.fitmaincharacter.data.WorkoutUnit
import com.inspiredandroid.fitmaincharacter.screens.app.SetupViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun DifficultyScreen(
    viewModel: SetupViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToWorkout: () -> Unit,
) {
    val uiState by viewModel.state.collectAsState()

    SelectDifficultyContent(
        difficulties = uiState.difficulties,
        workout = uiState.workout,
        onNavigateBack = onNavigateBack,
        onContinueClick = onNavigateToWorkout,
        toggleDifficulty = uiState.toggleDifficulty,
    )
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
                        Modifier.height(48.dp),
                        tint = SoftSand,
                    )
                }
            }
        }
    }
}
