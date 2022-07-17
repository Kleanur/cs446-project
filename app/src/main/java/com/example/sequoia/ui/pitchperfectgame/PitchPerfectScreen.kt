package com.example.sequoia.ui.pitchperfectgame

import android.app.Application
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PitchPerfectScreen(
    pitchPerfectViewModel: PitchPerfectViewModel = PitchPerfectViewModel(application = LocalContext.current.applicationContext as Application),
    navController: NavController
) {
    val context = LocalContext.current
    val checkedStateOne = remember { mutableStateOf(false) }
    val checkedStateTwo = remember { mutableStateOf(false) }

    var firstAnswer: PitchPerfectViewModel.Song? = null
    var secondAnswer: PitchPerfectViewModel.Song? = null

    SequoiaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ConstraintLayout {
                // Create references for the composable to constrain
                val (playBtn, txtTwo, songOne, songTwo, checkOne, checkTwo) = createRefs()


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
//
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


                val infiniteTransition = rememberInfiniteTransition()
                val dy by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                val travelDistance = with(LocalDensity.current) { 30.dp.toPx() }


                Button(
                    onClick = {
                        pitchPerfectViewModel.chooseAnswerPitchSong(
                            0
                        )?.let { song ->
                            firstAnswer = song
                            pitchPerfectViewModel.playRandomSongForTenSeconds(song)
                        }
                    },
                    modifier = Modifier.constrainAs(songOne) {
                        top.linkTo(txtTwo.bottom, margin = 80.dp)
                        start.linkTo(parent.start, margin = 70.dp)
                    },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white))
                ) {

                    Icon(
                        painter = painterResource(R.drawable.pitchperfect_icon),
                        modifier = Modifier
                            .size(40.dp)
                            .graphicsLayer {
                                translationY = dy * travelDistance
                            },
                        contentDescription = "music button content.",
                        tint = colorResource(id = R.color.purple_700),
                    )
                }

                Button(
                    onClick = {
                        pitchPerfectViewModel.chooseAnswerPitchSong(
                            1
                        )?.let { song ->
                            secondAnswer = song
                            pitchPerfectViewModel.playRandomSongForTenSeconds(song)
                        }
                    },
                    modifier = Modifier.constrainAs(songTwo) {
                        top.linkTo(txtTwo.bottom, margin = 80.dp)
                        start.linkTo(songOne.end, margin = 50.dp)
                    },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.white))
                ) {

                    Icon(
                        painter = painterResource(R.drawable.pitchperfect_icon),
                        modifier = Modifier.size(40.dp),
                        contentDescription = "music button content.",
                        tint = colorResource(id = R.color.purple_700),
                    )
                }

                // in below line we are displaying a row
                // and we are creating a checkbox in a row.
                Row(modifier = Modifier.constrainAs(checkOne) {
                    top.linkTo(songOne.bottom, margin = 20.dp)
                    start.linkTo(songOne.start)

                }) {

                    Checkbox(
                        // below line we are setting
                        // the state of checkbox.
                        checked = checkedStateOne.value,
                        // below line is use to add padding
                        // to our checkbox.
                        modifier = Modifier.padding(16.dp),
                        // below line is use to add on check
                        // change to our checkbox.
                        onCheckedChange = {
                            if (it) {
                                checkedStateTwo.value = false
                                pitchPerfectViewModel.updateSelectedPitchAnswerSong(firstAnswer)
                                if (pitchPerfectViewModel.verifyAnswer()) {
                                    Toast.makeText(
                                        context,
                                        "YAY! You won the game!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    // Show the true results - Give score or go to next round
                                } else {
                                    Toast.makeText(
                                        context,
                                        "FUCK YOU! You lost the game!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    // Show a dialog box that says answer is wrong. Try again and reset everything here.
                                }
                            }
                            checkedStateOne.value = it
                        },
                    )

                }

                Row(modifier = Modifier.constrainAs(checkTwo) {
                    top.linkTo(songTwo.bottom, margin = 20.dp)
                    start.linkTo(songTwo.start)

                }) {

                    Checkbox(
                        // below line we are setting
                        // the state of checkbox.
                        checked = checkedStateTwo.value,
                        // below line is use to add padding
                        // to our checkbox.
                        modifier = Modifier.padding(16.dp),
                        // below line is use to add on check
                        // change to our checkbox.
                        onCheckedChange = {
                            if (it) {
                                checkedStateOne.value = false
                                pitchPerfectViewModel.updateSelectedPitchAnswerSong(secondAnswer)
                                if (pitchPerfectViewModel.verifyAnswer()) {
                                    Toast.makeText(
                                        context,
                                        "YAY! You won the game!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    // Show the true results - Give score or go to next round
                                } else {
                                    Toast.makeText(
                                        context,
                                        "You lost the game!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    // Show a dialog box that says answer is wrong. Try again and reset everything here.
                                }
                            }
                            checkedStateTwo.value = it
                        },
                    )

                }
            }
        }
    }
}