package com.example.sequoia.route

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Games: Routes("games")
    object History : Routes("history")
    object  SimonSaysGame: Routes("simonSaysGame")
    object  Settings: Routes("settings")
    object GotRythmGame: Routes("gotrythmgame")
    object LicketySplitGame: Routes("licketySplitGame")
    object  PitchPerfectGame: Routes("pitchperfectgame")
}
