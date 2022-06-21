package com.example.sequoia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sequoia.route.Routes
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
                    SimonSaysGameScreen()
                }
            }
        }
    }


}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun HomeScreen(mainViewModel: MainViewModel = viewModel(), navController: NavController) {
    SequoiaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ConstraintLayout {
                // Create references for the composables to constrain
                val (treeTmg, historyBtn, settingBtn, gamesBtn, nameHeaderTxt) = createRefs()

                Text(
                    text = "Sequoia", fontSize = 32.sp, fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(nameHeaderTxt) {
                        top.linkTo(parent.top, margin = 48.dp)
                        start.linkTo(parent.start, margin = 135.dp)
                        end.linkTo(parent.end, margin = 48.dp)
                        width = Dimension.matchParent
                    },
                    textAlign = TextAlign.Start,
                )

                Box(
                    modifier = Modifier
                        .constrainAs(gamesBtn) {
                            top.linkTo(nameHeaderTxt.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.Games.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.play_button_background_olive_green))
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_play_arrow),
                            contentDescription = "Play button content description.",
                            tint = Color.Unspecified,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Play",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.play_text_dark_green),
                            textAlign = TextAlign.Start,
                        )
                    }
//
                }

                Box(
                    modifier = Modifier
                        .constrainAs(settingBtn) {
                            top.linkTo(gamesBtn.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.Games.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.setting_button_background_light_purple))
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_settings),
                            contentDescription = "Setting button content description.",
                            tint = Color.Unspecified,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Settings",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.setting_button_dark_purple),
                            textAlign = TextAlign.Start,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .constrainAs(historyBtn) {
                            top.linkTo(settingBtn.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.Games.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.history_button_background_light_blue))
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_account_box),
                            contentDescription = "History button content description.",
                            tint = Color.Unspecified,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "History",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.history_button_dark_blue),
                            textAlign = TextAlign.Start,
                        )
                    }
                }
                Image(painter = painterResource(R.drawable.tree_home), contentDescription = "Image for Home Screen",  modifier = Modifier
                    .constrainAs(treeTmg) {
                        top.linkTo(historyBtn.bottom, margin = 24.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .height(240.dp)
                    .padding(start = 16.dp, end = 16.dp) )

            }
        }
    }
}

@Composable
fun GamesScreen(mainViewModel: MainViewModel = viewModel(), navController: NavController) {
    SequoiaTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ConstraintLayout {
                // Create references for the composables to constrain
                val (LicketySplitBtn, SimonSaysBtn, PitchPerfectBtn, GotRythmBtn, nameHeaderTxt) = createRefs()

                Text(
                    text = "Games", fontSize = 32.sp, fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.Games_txt_green),
                    modifier = Modifier.constrainAs(nameHeaderTxt) {
                        top.linkTo(parent.top, margin = 48.dp)
                        start.linkTo(parent.start, margin = 135.dp)
                        end.linkTo(parent.end, margin = 48.dp)
                        width = Dimension.matchParent
                    },
                    textAlign = TextAlign.Start,
                )

                Box(
                    modifier = Modifier
                        .constrainAs(SimonSaysBtn) {
                            top.linkTo(nameHeaderTxt.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.SimonSaysGame.route) {
                                popUpTo(Routes.Games.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.Game_button_background_dark_green))
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.simonsays_icon),
                            contentDescription = "Play button content description.",
                            tint = Color.Unspecified,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Simon Says",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Start,
                        )
                    }
//
                }

                Box(
                    modifier = Modifier
                        .constrainAs(PitchPerfectBtn) {
                            top.linkTo(SimonSaysBtn.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.SimonSaysGame.route) {
                                popUpTo(Routes.Games.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.Game_button_background_dark_green))
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.pitchperfect_icon),
                            contentDescription = "Setting button content description.",
                            tint = Color.Unspecified,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Pitch Perfect",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Start,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .constrainAs(GotRythmBtn) {
                            top.linkTo(PitchPerfectBtn.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.SimonSaysGame.route) {
                                popUpTo(Routes.Games.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.Game_button_background_dark_green))
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.gotrhythm_icon),
                            contentDescription = "History button content description.",
                            tint = Color.Unspecified,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Got Rhythm",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Start,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .constrainAs(LicketySplitBtn) {
                            top.linkTo(GotRythmBtn.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.SimonSaysGame.route) {
                                popUpTo(Routes.Games.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.Game_button_background_dark_green))
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.licketysplit_icon),
                            contentDescription = "History button content description.",
                            tint = Color.Unspecified,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Lickety Split",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.white),
                            textAlign = TextAlign.Start,
                        )
                    }
                }


            }
        }
    }
}



@Composable
fun SimonSaysGameScreen() {
    SequoiaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Greeting("Simon Says Game")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SequoiaTheme {
        Greeting("Android")
    }
}