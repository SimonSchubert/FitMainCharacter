package com.inspiredandroid.fitmaincharacter.screens.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.inspiredandroid.fitmaincharacter.ScreenBackground
import com.inspiredandroid.fitmaincharacter.screens.setup.DifficultyScreen
import com.inspiredandroid.fitmaincharacter.screens.setup.ExercisesScreen
import com.inspiredandroid.fitmaincharacter.screens.workout.WorkoutScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class Screen(val route: String) {
    Exercises("Exercises"),
    Difficulty("Difficulty"),
    Workout("Workout"),
}

@Composable
@Preview
fun App(
    viewModel: SetupViewModel = viewModel { SetupViewModel() },
    onStartWorkout: () -> Unit = {},
    onFinishWorkout: () -> Unit = {},
) {
    MaterialTheme {
        val uiState by viewModel.state.collectAsState()
        val navController = rememberNavController()

        Box(
            Modifier.fillMaxSize().background(ScreenBackground).statusBarsPadding()
                .navigationBarsPadding(),
        ) {
            NavHost(navController, startDestination = Screen.Exercises.route) {
                composable(Screen.Exercises.route) {
                    ExercisesScreen(
                        viewModel = viewModel,
                        onNavigateToDifficulty = {
                            navController.navigate(Screen.Difficulty.route)
                        },
                    )
                }
                composable(Screen.Difficulty.route) {
                    DifficultyScreen(
                        viewModel = viewModel,
                        onNavigateBack = {
                            navController.navigateUp()
                        },
                        onNavigateToWorkout = {
                            navController.navigate(Screen.Workout.route)
                            onStartWorkout()
                        },
                    )
                }
                composable(Screen.Workout.route) {
                    WorkoutScreen(
                        workout = uiState.workout,
                        onFinish = {
                            navController.navigate(Screen.Exercises.route)
                            onFinishWorkout()
                        },
                    )
                }
            }
        }
    }
}
