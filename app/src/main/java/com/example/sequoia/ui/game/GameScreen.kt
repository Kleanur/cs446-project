package com.example.sequoia.ui.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sequoia.route.Routes
import com.example.sequoia.R
import com.example.sequoia.ui.theme.SequoiaTheme

@Composable
fun GamesScreen(mainViewModel: GameViewModel = viewModel(), navController: NavController) {
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
                    color = colorResource(id = R.color.games_txt_green),
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
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.game_button_background_dark_green))
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
                            navController.navigate(Routes.PitchPerfectGame.route) {
                                popUpTo(Routes.Games.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.game_button_background_dark_green))
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
                            navController.navigate(Routes.GotRythmGame.route) {
                                popUpTo(Routes.Games.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.game_button_background_dark_green))
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
                            navController.navigate(Routes.LicketySplitGame.route) {
                                popUpTo(Routes.Games.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.game_button_background_dark_green))
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