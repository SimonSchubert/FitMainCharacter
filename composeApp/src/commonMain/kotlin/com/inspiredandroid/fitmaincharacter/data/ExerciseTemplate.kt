package com.inspiredandroid.fitmaincharacter.data

import fitmaincharacter.composeapp.generated.resources.Res
import fitmaincharacter.composeapp.generated.resources.exercise_jumping_jacks
import fitmaincharacter.composeapp.generated.resources.exercise_pullup
import fitmaincharacter.composeapp.generated.resources.exercise_pushup
import fitmaincharacter.composeapp.generated.resources.exercise_situp
import org.jetbrains.compose.resources.DrawableResource

enum class WorkoutUnit {
    SECOND,
    REPS,
}

enum class WorkoutType {
    WARMUP,
    STANDARD,
}

data class ExerciseTemplate(val id: Long, val name: String, val imageRes: DrawableResource, val baseCount: Int, val unit: WorkoutUnit, val type: WorkoutType)

val allExerciseTemplates = listOf(
    ExerciseTemplate(0, "Jumping Jacks", Res.drawable.exercise_jumping_jacks, 60, WorkoutUnit.SECOND, WorkoutType.WARMUP),
    ExerciseTemplate(1, "Pull ups", Res.drawable.exercise_pullup, 2, WorkoutUnit.REPS, WorkoutType.STANDARD),
    ExerciseTemplate(2, "Push ups", Res.drawable.exercise_pushup, 5, WorkoutUnit.REPS, WorkoutType.STANDARD),
    ExerciseTemplate(3, "Sit ups", Res.drawable.exercise_situp, 5, WorkoutUnit.REPS, WorkoutType.STANDARD),
)

fun ExerciseTemplate.toWorkoutExercise(difficulty: Difficulty): Workout.WorkoutExercise {
    return Workout.WorkoutExercise(
        name = this.name,
        imageRes = this.imageRes,
        count = if (unit == WorkoutUnit.REPS) (baseCount * difficulty.multiplier).toInt() else baseCount,
        unit = this.unit,
        type = this.type,
    )
}
