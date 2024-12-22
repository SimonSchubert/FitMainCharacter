package com.inspiredandroid.fitmaincharacter.screens.app

import com.inspiredandroid.fitmaincharacter.data.Workout

data class AppUiState(
    val page: Page = Page.Setup,
    val setPage: (Page) -> Unit = {},
    val startWorkout: (workout: Workout) -> Unit = {},
    val workout: Workout = Workout(),
) {
    enum class Page {
        Setup,
        Workout,
    }
}
