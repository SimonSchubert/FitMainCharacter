package com.inspiredandroid.fitmaincharacter.screens.app

import androidx.lifecycle.ViewModel
import com.inspiredandroid.fitmaincharacter.data.Workout
import com.inspiredandroid.fitmaincharacter.screens.app.AppUiState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        AppUiState(
            setPage = ::setPage,
            startWorkout = ::startWorkout,
        ),
    )
    val state: StateFlow<AppUiState> = _state.asStateFlow()

    fun setPage(page: Page) {
        _state.update {
            it.copy(page = page)
        }
    }

    fun startWorkout(workout: Workout) {
        _state.update {
            it.copy(
                page = Page.Workout,
                workout = workout,
            )
        }
    }
}
