package com.inspiredandroid.fitmaincharacter.data

import org.jetbrains.compose.resources.DrawableResource
import sport.composeapp.generated.resources.Res
import sport.composeapp.generated.resources.difficulty_burger
import sport.composeapp.generated.resources.difficulty_carrot
import sport.composeapp.generated.resources.difficulty_chicken
import sport.composeapp.generated.resources.difficulty_egg
import sport.composeapp.generated.resources.difficulty_pizza

data class Difficulty(val id: Long, val name: String, val imageRes: DrawableResource, val sets: Int, val multiplier: Float)

val allDifficulties = listOf(
    Difficulty(id = 0, name = "Carrot", imageRes = Res.drawable.difficulty_carrot, sets = 1, multiplier = 1.0f),
    Difficulty(id = 1, name = "Egg", imageRes = Res.drawable.difficulty_egg, sets = 2, multiplier = 1.5f),
    Difficulty(id = 2, name = "Chicken", imageRes = Res.drawable.difficulty_chicken, sets = 3, multiplier = 2.0f),
    Difficulty(id = 3, name = "Pizza", imageRes = Res.drawable.difficulty_pizza, sets = 4, multiplier = 3.0f),
    Difficulty(id = 4, name = "Burger", imageRes = Res.drawable.difficulty_burger, sets = 5, multiplier = 4.0f),
)
