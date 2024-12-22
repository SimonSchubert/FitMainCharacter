package com.inspiredandroid.fitmaincharacter.data

import org.jetbrains.compose.resources.DrawableResource

data class Workout(
    val id: String = "",
    val rounds: List<Round> = emptyList(),
) {
    data class Round(
        val exercises: List<WorkoutExercise>,
    )

    data class WorkoutExercise(
        val name: String,
        val imageRes: DrawableResource,
        val count: Int,
        val unit: WorkoutUnit,
        val type: WorkoutType,
    )

    data class WorkoutRest(
        val name: String,
        val imageRes: DrawableResource,
        val seconds: Int,
        val type: RestType,
    ) {
        enum class RestType {
            START,
            REST,
        }
    }
}
