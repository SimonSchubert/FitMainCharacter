package com.inspiredandroid.fitmaincharacter.screens.workout

import androidx.lifecycle.ViewModel
import com.inspiredandroid.fitmaincharacter.data.Workout
import com.inspiredandroid.fitmaincharacter.data.WorkoutType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import sport.composeapp.generated.resources.Res
import sport.composeapp.generated.resources.exercise_rest
import sport.composeapp.generated.resources.exercise_stretching

class WorkoutViewModel(val workout: Workout) : ViewModel() {

    private val _state = MutableStateFlow(
        WorkoutUiState(
            onFinishExercise = ::nextExercise,
            onFinishRest = ::finishRest,
        ),
    )
    val state: StateFlow<WorkoutUiState> = _state.asStateFlow()

    private var currentRounds = workout.rounds
        .map { it }
        .toMutableList()

    init {
        nextExercise()
    }

    fun finishRest() {
        _state.update {
            it.copy(currentRest = null)
        }
    }

    fun nextExercise() {
        val preciousExercise = _state.value.currentExercise
        val nextExercise = currentRounds.firstOrNull()?.exercises?.firstOrNull()

        if (nextExercise == null) {
            if (currentRounds.isEmpty()) {
                finishWorkout()
                return
            }
            currentRounds.removeAt(0)
            nextExercise()
            return
        }

        removePreviousExercise()

        val restExercise = if (preciousExercise == null) {
            Workout.WorkoutRest(
                name = "Get Ready",
                imageRes = Res.drawable.exercise_stretching,
                seconds = 10,
                type = Workout.WorkoutRest.RestType.START,
            )
        } else {
            Workout.WorkoutRest(
                name = "Rest",
                imageRes = Res.drawable.exercise_rest,
                seconds = if (preciousExercise.type == WorkoutType.WARMUP) {
                    30
                } else {
                    60
                },
                type = Workout.WorkoutRest.RestType.REST,
            )
        }

        val currentRoundIndex = workout.rounds.size - currentRounds.size
        val currentExerciseIndex = (workout.rounds.getOrNull(currentRoundIndex)?.exercises?.indexOf(nextExercise) ?: 0)

        _state.update {
            it.copy(
                currentExercise = nextExercise,
                currentRest = restExercise,
                roundProgress = "${currentRoundIndex + 1}/${workout.rounds.size}",
                exerciseSize = workout.rounds.getOrNull(currentRoundIndex)?.exercises?.size ?: 0,
                exerciseIndex = currentExerciseIndex,
            )
        }
    }

    private fun removePreviousExercise() {
        currentRounds = currentRounds.mapIndexed { index, round ->
            if (index == 0) {
                val exercises = round.exercises.toMutableList()
                exercises.removeAt(0)
                round.copy(exercises = exercises)
            } else {
                round
            }
        }.toMutableList()
    }

    private fun finishWorkout() {
        _state.update {
            it.copy(
                isFinished = true,
            )
        }
    }
}
