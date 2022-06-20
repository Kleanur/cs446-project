package com.example.sequoia.route

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Games: Routes("games")
    object  SimonSaysGame: Routes("simonSaysGame")
}
