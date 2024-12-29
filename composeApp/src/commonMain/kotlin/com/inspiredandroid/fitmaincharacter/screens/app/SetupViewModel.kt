package com.inspiredandroid.fitmaincharacter.screens.app

import androidx.lifecycle.ViewModel
import com.inspiredandroid.fitmaincharacter.data.Workout
import com.inspiredandroid.fitmaincharacter.data.WorkoutType
import com.inspiredandroid.fitmaincharacter.data.allDifficulties
import com.inspiredandroid.fitmaincharacter.data.allExerciseTemplates
import com.inspiredandroid.fitmaincharacter.data.toWorkoutExercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class SetupViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        SetupUiState(
            toggleExercise = ::toggleExercise,
            toggleDifficulty = ::toggleDifficulty,
        ),
    )
    val state: StateFlow<SetupUiState> = _state.asStateFlow()

    private fun toggleExercise(id: Long) {
        _state.update {
            it.copy(
                exercises = it.exercises.map { exercise ->
                    if (exercise.id == id) {
                        exercise.copy(isSelected = !exercise.isSelected)
                    } else {
                        exercise
                    }
                },
            )
        }
        buildWorkout()
    }

    private fun buildWorkout() {
        val selectedDifficulty = state.value.difficulties.firstOrNull { it.isSelected }?.let { allDifficulties.firstOrNull { d -> d.id == it.id } } ?: return
        val selectedExercises = state.value.exercises.filter { it.isSelected }.mapNotNull { allExerciseTemplates.firstOrNull { e -> e.id == it.id } }
        val warmups = selectedExercises.filter { it.type == WorkoutType.WARMUP }
        val rounds = buildList {
            repeat(selectedDifficulty.sets) { index ->
                val exercises = buildList {
                    warmups.getOrNull(index)?.let { add(it.toWorkoutExercise(selectedDifficulty)) }
                    selectedExercises.filter { it.type == WorkoutType.STANDARD }.forEach { exercise ->
                        add(exercise.toWorkoutExercise(selectedDifficulty))
                    }
                }
                if (exercises.isNotEmpty()) {
                    add(Workout.Round(exercises))
                }
            }
        }

        _state.update {
            it.copy(
                workout = Workout(
                    id = Random.nextLong().toString(),
                    rounds = rounds,
                ),
            )
        }
    }

    private fun toggleDifficulty(id: Long) {
        _state.update {
            it.copy(
                difficulties = it.difficulties.map { difficulty ->
                    difficulty.copy(isSelected = difficulty.id == id)
                },
            )
        }
        buildWorkout()
    }
}
