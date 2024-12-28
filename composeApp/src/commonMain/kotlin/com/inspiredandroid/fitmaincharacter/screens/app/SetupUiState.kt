package com.inspiredandroid.fitmaincharacter.screens.app

import com.inspiredandroid.fitmaincharacter.components.Selectable
import com.inspiredandroid.fitmaincharacter.data.Workout
import com.inspiredandroid.fitmaincharacter.data.allDifficulties
import com.inspiredandroid.fitmaincharacter.data.allExerciseTemplates

data class SetupUiState(
    val exercises: List<Selectable> = allExerciseTemplates.map {
        Selectable(
            id = it.id,
            name = it.name,
            imageRes = it.imageRes,
        )
    },
    val difficulties: List<Selectable> = allDifficulties.map {
        Selectable(
            id = it.id,
            name = it.name,
            imageRes = it.imageRes,
            isSelected = it.id == 2L,
        )
    },
    val toggleExercise: (Long) -> Unit = {},
    val toggleDifficulty: (Long) -> Unit = {},
    val workout: Workout = Workout(),
)
