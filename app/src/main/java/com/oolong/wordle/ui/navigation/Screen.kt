package com.oolong.wordle.ui.navigation

sealed class Screen(val route: String) {
    data object GameBoardScreen : Screen(route = "game_board_screen")
    data object StartScreen : Screen(route = "start_screen")
    data object EndGameScreen : Screen(route = "end_game_screen")
}

sealed class GraphGroup(val group: String) {
    data object Game : GraphGroup(group = "game")
}