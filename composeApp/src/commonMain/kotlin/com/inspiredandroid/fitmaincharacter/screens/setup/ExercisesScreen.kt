@file:OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)

package com.inspiredandroid.fitmaincharacter.screens.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inspiredandroid.fitmaincharacter.CardBackground
import com.inspiredandroid.fitmaincharacter.SoftSand
import com.inspiredandroid.fitmaincharacter.SportFontFamily
import com.inspiredandroid.fitmaincharacter.components.LightButton
import com.inspiredandroid.fitmaincharacter.components.Selectable
import com.inspiredandroid.fitmaincharacter.components.TopBar
import com.inspiredandroid.fitmaincharacter.screens.app.SetupViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun ExercisesScreen(
    viewModel: SetupViewModel,
    onNavigateToDifficulty: () -> Unit,
) {
    val uiState by viewModel.state.collectAsState()

    ExercisesContent(
        exercises = uiState.exercises,
        onContinueClick = onNavigateToDifficulty,
        toggleExercise = uiState.toggleExercise,
    )
}

@Composable
private fun ExercisesContent(
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
            .pointerHoverIcon(PointerIcon.Hand)
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
