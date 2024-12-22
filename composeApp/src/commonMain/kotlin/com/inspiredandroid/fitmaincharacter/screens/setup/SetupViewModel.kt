package com.inspiredandroid.fitmaincharacter.screens.setup

import androidx.lifecycle.ViewModel
import com.inspiredandroid.fitmaincharacter.data.Workout
import com.inspiredandroid.fitmaincharacter.data.WorkoutType
import com.inspiredandroid.fitmaincharacter.data.allDifficulties
import com.inspiredandroid.fitmaincharacter.data.allExerciseTemplates
import com.inspiredandroid.fitmaincharacter.data.toWorkoutExercise
import com.inspiredandroid.fitmaincharacter.screens.setup.SetupUiState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class SetupViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        SetupUiState(
            setPage = ::setPage,
            toggleExercise = ::toggleExercise,
            toggleDifficulty = ::toggleDifficulty,
            reset = ::reset,
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
    }

    private fun buildWorkout() {
        val selectedDifficulty = state.value.difficulties.firstOrNull { it.isSelected }?.let { allDifficulties.firstOrNull { d -> d.id == it.id } } ?: return
        val selectedExercises = state.value.exercises.filter { it.isSelected }.mapNotNull { allExerciseTemplates.firstOrNull { e -> e.id == it.id } }

        val rounds = buildList {
            repeat(selectedDifficulty.sets) { index ->
                val exercises = buildList {
                    if (index == 0) {
                        selectedExercises.firstOrNull { it.type == WorkoutType.WARMUP }?.let { warmup ->
                            add(warmup.toWorkoutExercise(selectedDifficulty))
                        }
                    }
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

    private fun setPage(page: Page) {
        _state.update { it.copy(page = page) }
        if (page == Page.SelectDifficulty) {
            buildWorkout()
        }
    }

    private fun reset() {
        _state.update {
            it.copy(
                page = Page.SelectExercises,
            )
        }
    }
}
