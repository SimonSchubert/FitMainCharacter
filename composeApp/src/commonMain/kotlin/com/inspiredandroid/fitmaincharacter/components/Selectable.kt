package com.inspiredandroid.fitmaincharacter.components

import org.jetbrains.compose.resources.DrawableResource

data class Selectable(
    val id: Long,
    val name: String,
    val imageRes: DrawableResource,
    val isSelected: Boolean = false,
)
