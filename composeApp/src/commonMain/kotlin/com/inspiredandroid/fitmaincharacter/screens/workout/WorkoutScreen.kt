package com.inspiredandroid.fitmaincharacter.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inspiredandroid.fitmaincharacter.LargeCountdownStyle
import com.inspiredandroid.fitmaincharacter.MediumTextStyle
import com.inspiredandroid.fitmaincharacter.SmallTextStyle
import com.inspiredandroid.fitmaincharacter.SoftSand
import com.inspiredandroid.fitmaincharacter.SportFontFamily
import com.inspiredandroid.fitmaincharacter.components.AnimatedNumber
import com.inspiredandroid.fitmaincharacter.components.LightButton
import com.inspiredandroid.fitmaincharacter.data.Workout
import com.inspiredandroid.fitmaincharacter.data.WorkoutUnit
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import sport.composeapp.generated.resources.Res
import sport.composeapp.generated.resources.finish

@Composable
@Preview
fun WorkoutScreen(
    workout: Workout,
    onFinish: () -> Unit,
    viewModel: WorkoutViewModel = androidx.lifecycle.viewmodel.compose.viewModel(key = workout.id) { WorkoutViewModel(workout) },
) {
    val uiState by viewModel.state.collectAsState()

    if (uiState.isFinished) {
        Finished(onFinish = onFinish)
    } else {
        val currentExercise = uiState.currentExercise

        if (currentExercise != null) {
            val currentRest = uiState.currentRest
            if (currentRest != null) {
                RestCountdown(
                    currentExercise = currentExercise,
                    currentRest = currentRest,
                    exerciseSize = uiState.exerciseSize,
                    exerciseIndex = uiState.exerciseIndex,
                    roundProgress = uiState.roundProgress,
                    onFinishRest = uiState.onFinishRest,
                )
            } else {
                ActiveWorkout(
                    exercise = currentExercise,
                    onNextExercise = { uiState.nextExercise() },
                )
            }
        }
    }
}

@Composable
private fun Finished(
    onFinish: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(Res.drawable.finish),
            null,
            Modifier.height(160.dp),
            tint = SoftSand,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Great job".uppercase(),
            style = MaterialTheme.typography.h6,
            fontSize = 18.sp,
            color = SoftSand,
            fontFamily = SportFontFamily(),
        )
        Spacer(Modifier.weight(1f))

        LightButton(
            onClick = onFinish,
            text = "Finish",
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun RestCountdown(
    currentExercise: Workout.WorkoutExercise,
    currentRest: Workout.WorkoutRest,
    exerciseSize: Int,
    exerciseIndex: Int,
    roundProgress: String,
    onFinishRest: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))

        if (currentRest.type == Workout.WorkoutRest.RestType.REST) {
            Spacer(Modifier.height(24.dp))

            Row(
                Modifier.padding(horizontal = 24.dp).widthIn(max = 500.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    roundProgress,
                    style = SmallTextStyle(),
                )
                Box(Modifier.size(6.dp))
                repeat(exerciseSize) { index ->
                    val color = if (index <= exerciseIndex) {
                        SoftSand
                    } else {
                        SoftSand.copy(alpha = 0.3f)
                    }
                    Box(Modifier.height(6.dp).weight(1f).background(color))
                    Box(Modifier.size(6.dp))
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        ExerciseInfo(
            imageRes = currentRest.imageRes,
            title = currentRest.name,
        )

        Spacer(Modifier.height(48.dp))

        SecondsCountdown(
            count = currentRest.seconds,
            nextExercise = onFinishRest,
        )

        Spacer(Modifier.weight(1f))

        Text(
            "Up next:",
            style = MediumTextStyle(),
        )

        Spacer(Modifier.height(6.dp))

        ExercisePreview(
            exercise = currentExercise,
        )

        Spacer(Modifier.weight(1f))

        LightButton(
            onClick = onFinishRest,
            text = "Skip",
        )

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun ActiveWorkout(exercise: Workout.WorkoutExercise, onNextExercise: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(1f))

        ExerciseInfo(
            imageRes = exercise.imageRes,
            title = exercise.name,
        )

        Spacer(Modifier.height(32.dp))

        when (exercise.unit) {
            WorkoutUnit.REPS -> {
                RepsCountdown(exercise = exercise)

                Spacer(Modifier.weight(1f))

                LightButton(
                    onClick = onNextExercise,
                    text = "Done",
                )

                Spacer(Modifier.height(16.dp))
            }

            WorkoutUnit.SECOND -> {
                SecondsCountdown(
                    count = exercise.count,
                    nextExercise = onNextExercise,
                )

                Spacer(Modifier.weight(1f))

                LightButton(
                    onClick = onNextExercise,
                    text = "Done",
                )

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ExerciseInfo(imageRes: DrawableResource, title: String) {
    Icon(
        painterResource(imageRes),
        null,
        Modifier.height(160.dp),
        tint = SoftSand,
    )
    Spacer(Modifier.height(12.dp))
    Text(
        title.uppercase(),
        style = MaterialTheme.typography.h6,
        fontSize = 18.sp,
        color = SoftSand,
        fontFamily = SportFontFamily(),
    )
}

@Composable
private fun ExercisePreview(exercise: Workout.WorkoutExercise) {
    Icon(
        painterResource(exercise.imageRes),
        null,
        Modifier.height(56.dp),
        tint = SoftSand,
    )
    Spacer(Modifier.height(12.dp))
    Text(
        exercise.name.uppercase(),
        style = MaterialTheme.typography.h6,
        fontSize = 16.sp,
        color = SoftSand,
        fontFamily = SportFontFamily(),
    )
}

@Composable
private fun RepsCountdown(exercise: Workout.WorkoutExercise) {
    Text(
        text = exercise.count.toString(),
        style = LargeCountdownStyle(),
    )
    Text(
        text = "reps",
        style = MediumTextStyle(),
    )
}

@Composable
private fun SecondsCountdown(count: Int, nextExercise: () -> Unit) {
    var timeLeft by remember { mutableStateOf(count) }
    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        nextExercise()
    }
    Row {
        AnimatedNumber(
            number = timeLeft,
            style = LargeCountdownStyle(),
        )
    }
    Text(
        text = "seconds",
        style = MediumTextStyle(),
    )
}
