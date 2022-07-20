package com.example.sequoia.ui.gotrythm

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sequoia.route.Routes
import com.example.sequoia.ui.repository.ScoreImpl
import com.example.sequoia.ui.theme.*

@Composable
fun gotrythmGameScreen(nc: NavController) {
    SequoiaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = GotRhythmBackgroundColor
        ) {
            DrawGotRythmBoard(nc)
        }
    }
}

@Composable
fun DrawGotRythmBoard(nc: NavController) {
    val viewModel = viewModel<GotRythmViewModel>()
    val viewState = viewModel.viewState.value

    val c = LocalContext.current
    val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            c.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        c.getSystemService(ComponentActivity.VIBRATOR_SERVICE) as Vibrator
    }


    ConstraintLayout {
        val (gamebutton, score) = createRefs()

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(score) {
                    top.linkTo(parent.top, margin = 20.dp)
                }) {
            Text(
                text = "Chances: ${viewState.attemptsLeft}",
                fontSize = 20.sp
            )
            if (!viewState.gameRunning) {
                Button(onClick = {
                    viewModel.init(vib)
                }
                ) {
                    Text(
                        text = "Start",
                        fontSize = 20.sp
                    )
                }
            }
            Text(
                text = "Scores: ${viewState.score}",
                fontSize = 20.sp
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.constrainAs(gamebutton) {
                top.linkTo(score.bottom, margin = 150.dp)
                start.linkTo(parent.start, margin = 0.dp)
                end.linkTo(parent.end, margin = 0.dp)
            }
        ) {
            CreateButton(viewState.counter, viewState.playerTurn, viewModel, vib)
        }
    }

    if (viewState.attemptsLeft == 0) {
        val context = LocalContext.current
        AlertDialog(onDismissRequest = {},
            title = {Text(text = "Game Completed")},
            text = {Text("Your Score: ${viewState.score}")},
            confirmButton = {
                Button(onClick = {
                    saveScore(viewState.score, context)
                    viewModel.reset() }) {
                    Text("Retry?")
                }},
            dismissButton = {
                Button(onClick = {
                    saveScore(viewState.score, context)
                    nc.navigate(Routes.Games.route) {
                        popUpTo(Routes.Home.route)
                    } }) {
                    Text("Back to game menu")
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateButton(counter: Int, pt:Boolean, viewModel: GotRythmViewModel, v:Vibrator) {
    val button = GotRhythmButtonColor
    val press = GotRhythmPressedColor
    val color = remember{ mutableStateOf(button)}


    Button(
        onClick = {}, // blank; overwritten by pointerInteropFilter
        shape = CircleShape,
        modifier = Modifier
            .padding(10.dp)
            .size(200.dp, 200.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (pt) {
                            color.value = press
                            viewModel.st = System.currentTimeMillis()
                            // testing purpose
                            println("vibrating")
                            v.vibrate(
                                VibrationEffect.createOneShot(
                                    viewModel.timecalc(),
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (pt) {
                            color.value = button
                            //testing purpose
                            println("stopped")
                            viewModel.recieveinput(System.currentTimeMillis())
                            v.cancel()
                        }
                    }
                }
                true
            },
        colors = ButtonDefaults.buttonColors(containerColor = color.value)) {
        if (counter > 0) {
            Text(text = "$counter", fontSize = 50.sp)
        }
    }
}

fun saveScore(score: Int, context: Context) {
    val scoreObj = ScoreImpl()
    gameIds["GotRhythm"]?.let {
        scoreObj.addScore(gameId = it,
            gameScore = score, context = context)
    }
}

