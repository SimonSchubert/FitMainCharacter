package com.inspiredandroid.fitmaincharacter.screens.workout

import com.inspiredandroid.fitmaincharacter.data.Workout

data class WorkoutUiState(
    val currentExercise: Workout.WorkoutExercise? = null,
    val currentRest: Workout.WorkoutRest? = null,
    val onFinishExercise: () -> Unit,
    val isFinished: Boolean = false,
    val roundProgress: String = "",
    val exerciseSize: Int = 0,
    val exerciseIndex: Int = 0,
    val onFinishRest: () -> Unit,
)
