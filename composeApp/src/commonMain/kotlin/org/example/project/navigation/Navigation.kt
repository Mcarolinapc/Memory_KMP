package org.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.project.screens.GameScreen
import org.example.project.screens.HomeScreen
import org.example.project.screens.ResultadoScreen
import org.example.project.screens.SelectCategory
import org.example.project.viewModel.MemoryViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val memoryViewModel = remember { MemoryViewModel() } // creación manual

    NavHost(navController, startDestination = Home) {

        // Pantalla 1: Selección de dificultad
        composable<Home> {
            HomeScreen { dificultad ->
                navController.navigate(Type(dificultad))
            }
        }

        // Pantalla 2: Elegir tipo
        composable<Type> { backStackEntry ->
            val type = backStackEntry.toRoute<Type>()
            SelectCategory(
                dificultad = type.dificultad,
                navigateToGame = { tipo ->
                    navController.navigate(Game(type.dificultad, tipo))
                }
            )
        }

        // Pantalla 3: Juego
        composable<Game> { backStackEntry ->
            val game = backStackEntry.toRoute<Game>()
            GameScreen(
                dificultad = game.dificultad,
                tipo = game.tipo,
                viewModel = memoryViewModel,
                onFinish = {
                    navController.navigate(Resultado) // no pasamos resultado por argumento
                }
            )
        }

        // Pantalla 4: Resultado
        composable<Resultado> {
            ResultadoScreen(
                viewModel = memoryViewModel,
                navigateToInicio = {
                    memoryViewModel.reiniciarJuego()
                    navController.navigate(Home) {
                        popUpTo<Home> { inclusive = true }
                    }
                }
            )
        }
    }
}
