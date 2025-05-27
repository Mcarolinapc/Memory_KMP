package org.example.project.navigation

import kotlinx.serialization.Serializable

@Serializable

object Home

@Serializable
data class Type(val dificultad: String)

@Serializable

data class Game( val dificultad: String, val tipo:String)

@Serializable

object Resultado