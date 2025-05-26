package org.example.project.model

import org.jetbrains.compose.resources.DrawableResource

data class CardMemory(
    val uniqueId: Int,//para distinguir cartas duplicadas
    val imageResId: DrawableResource, //será igual en par
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false
)