package com.oolong.wordle.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oolong.wordle.ui.gameboard.GameBoardScreen
import com.oolong.wordle.ui.start.StartScreen

@Composable
fun WordleNavGraph() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    val startDestination = Screen.StartScreen.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.GameBoardScreen.route) {
            GameBoardScreen()
        }

        composable(Screen.StartScreen.route) {
            StartScreen(
                onDailyWordleClick = actions.navigateToGameBoardScreen
            )
        }

        composable(Screen.EndGameScreen.route) {
            StartScreen(
                onDailyWordleClick = actions.navigateToStartScreen
            )
        }
    }
}

class MainActions(private val navHostController: NavHostController) {
    val popBackStack: () -> Unit = {
        navHostController.popBackStack()
    }

    val navigateToGameBoardScreen: () -> Unit = {
        navHostController.navigate(Screen.GameBoardScreen.route)
    }

    val navigateToStartScreen: () -> Unit = {
        navHostController.navigate(Screen.StartScreen.route)
    }

    val navigateToEndGameScreen: () -> Unit = {
        navHostController.navigate(Screen.EndGameScreen.route)
    }
}