package com.example.sequoia


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sequoia.route.Routes
import com.example.sequoia.ui.game.GamesScreen
import com.example.sequoia.ui.gotrythm.gotrythmGameScreen
import com.example.sequoia.ui.home.HomeScreen
import com.example.sequoia.ui.history.HistoryScreen
import com.example.sequoia.ui.infoScreen.InfoScreen
import com.example.sequoia.ui.pitchperfectgame.PitchPerfectScreen
import com.example.sequoia.ui.licketysplitgame.LicketySplitGameScreen
import com.example.sequoia.ui.settings.SettingsScreen
import com.example.sequoia.ui.simonsaysgame.SimonSaysGameScreen
import com.example.sequoia.ui.theme.SequoiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = Routes.Home.route) {
                composable(route = Routes.Home.route) {
                    HomeScreen(navController = navController)
                }
                composable(route = Routes.Games.route) {
                    GamesScreen(navController = navController)
                }

                composable(route = Routes.SimonSaysGame.route) {
                    SimonSaysGameScreen(nc = navController)
                }

                composable(route = Routes.LicketySplitGame.route) {
                    LicketySplitGameScreen(nc = navController)
                }

                composable(route = Routes.History.route){
                    HistoryScreen(navController = navController)
                }
                composable(route = Routes.Settings.route) {
                    SettingsScreen(navController = navController)
                }

                composable(route = Routes.GotRythmGame.route) {
                    gotrythmGameScreen(nc = navController)
                }
                composable(route = Routes.PitchPerfectGame.route) {
                    PitchPerfectScreen(navController = navController)
                }
                composable(route = Routes.InfoScreen.route) {
                    InfoScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SequoiaTheme {
        Greeting("Android")
    }
}