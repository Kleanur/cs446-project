package com.example.sequoia.ui.pitchperfectgame

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.sequoia.R
import com.example.sequoia.ui.theme.SequoiaTheme
import kotlin.random.Random
import kotlin.random.nextInt


@Composable
fun PitchPerfectScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val pitchPerfectViewModel =
        remember { PitchPerfectViewModel(application = context.applicationContext as Application) }

    val gameRound = pitchPerfectViewModel.gameRoundState.collectAsState().value
    val playerAnswer = pitchPerfectViewModel.answerMutableState.collectAsState().value

    val randomlyGeneratedIndexes =
        pitchPerfectViewModel.randomlyGeneratedIndexes.collectAsState().value

    val iconColor = remember { mutableStateOf(R.color.purple_700) }

    var playerAttempt = 2

    SequoiaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            ConstraintLayout {
                // Create references for the composable to constrain
                val (playBtn, txtTwo, musics, verifBtn, scoreTxt) = createRefs()

                Row(modifier = Modifier.constrainAs(scoreTxt) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)

                }) {
                    Text(
                        text = "score: ${pitchPerfectViewModel.scoreState.collectAsState().value}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.game_button_background_dark_green),
                        textAlign = TextAlign.Start,
                    )
                }


                Box(
                    modifier = Modifier
                        .constrainAs(playBtn) {
                            top.linkTo(parent.top, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }

                        .height(200.dp)
                        .padding(top = 100.dp, start = 80.dp, end = 80.dp)
                ) {
                    Button(
                        onClick = {
                            pitchPerfectViewModel.choosePitchRandomSong()?.let { song ->
                                pitchPerfectViewModel.playRandomSongForTenSeconds(song)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.history_button_dark_blue))
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.pitchperfect_icon),
                            contentDescription = "music button content.",
                            tint = Color.Unspecified,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Play The Music",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.history_button_background_light_blue),
                            textAlign = TextAlign.Start,
                        )
                    }
                }
                Row(modifier = Modifier.constrainAs(txtTwo) {
                    top.linkTo(playBtn.bottom, margin = 60.dp)
                    start.linkTo(parent.start, margin = 40.dp)

                }) {
                    Text(
                        text = "Check the correct song",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.game_button_background_dark_green),
                        textAlign = TextAlign.Start,
                    )
                }

                Row(modifier = Modifier.constrainAs(musics) {
                    top.linkTo(txtTwo.bottom, margin = 80.dp)
                    centerHorizontallyTo(playBtn)
                }) {
                    for (i in 0..gameRound) {
                        pitchPerfectViewModel.populateRandomlyGeneratedMutableStateList()
                        IconButton(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .padding(8.dp),
                            onClick = {

                                pitchPerfectViewModel.chooseAnswerPitchSong(randomlyGeneratedIndexes[i])
                                    ?.let { song ->
                                        pitchPerfectViewModel.playRandomSongForTenSeconds(song)
                                        pitchPerfectViewModel.updateSelectedPitchAnswerSong(song)
                                    }
                            },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.pitchperfect_icon),
                                contentDescription = "music button content.",
                                tint = colorResource(id = iconColor.value),
                                modifier = Modifier
                                    .fillMaxSize(),
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .constrainAs(verifBtn) {
                            top.linkTo(musics.bottom, margin = 40.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(150.dp)
                        .padding(top = 100.dp, start = 100.dp, end = 100.dp)
                ) {
                    Button(
                        onClick = {
                            pitchPerfectViewModel.verifyAnswer()
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.game_button_background_dark_green))
                    ) {

                    }
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        text = "Verify answer",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.history_button_background_light_blue),
                        textAlign = TextAlign.Center,
                    )
                }

            }
            when (playerAnswer) {
                true -> {
                    pitchPerfectViewModel.resetPlayerAnswer()
                    pitchPerfectViewModel.nextRound()
                    Toast.makeText(
                        context,
                        "YAY! You won the game!",
                        Toast.LENGTH_SHORT
                    ).show()
                    pitchPerfectViewModel.addScore()
                }
                false -> {
                    if (playerAttempt == 0) {
                        Toast.makeText(
                            context,
                            "Game Over",
                            Toast.LENGTH_SHORT
                        ).show()
                        pitchPerfectViewModel.resetGame()
                        return@Surface
                    }
                    pitchPerfectViewModel.resetPlayerAnswer()
                    pitchPerfectViewModel.loseAttempt()
                    playerAttempt--
                    Toast.makeText(
                        context,
                        "Wrong Answer. Remaining attempts: $playerAttempt",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                null -> {}
            }
        }
    }
}


