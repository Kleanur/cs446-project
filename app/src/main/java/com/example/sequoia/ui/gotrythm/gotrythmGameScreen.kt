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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sequoia.R
import com.example.sequoia.route.Routes
import com.example.sequoia.ui.simonsaysgame.DrawSimonSaysBoard
import com.example.sequoia.ui.simonsaysgame.SimonSaysViewModel
import com.example.sequoia.ui.theme.SequoiaTheme

@Composable
fun gotrythmGameScreen(nc: NavController) {
    SequoiaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DrawGotRythmBoard(nc)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
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
                text = "Chances: ${viewState.attemptsLeft}"
            )
            if (!viewState.gameRunning) {
                Button(onClick = {
                    viewModel.init(vib)
                }
                ) {
                    Text(
                        text = "Start"
                    )
                }
            }
            Text(
                text = "Scores: ${viewState.score}"
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
            createbutton(viewState.counter, viewState.playerTurn, viewModel, vib)
        }
    }

    if (viewState.attemptsLeft == 0) {
        AlertDialog(onDismissRequest = {},
            title = {Text(text = "TEST IS DONE!")},
            text = {Text("Your Score: ${viewState.score}")},
            confirmButton = {
                Button(onClick = { viewModel.reset() }) {
                    Text("Retry?")
                }},
            dismissButton = {
                Button(onClick = {
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
fun createbutton(counter: Int, pt:Boolean, viewModel: GotRythmViewModel, v:Vibrator) {
    val button = colorResource(id = R.color.games_txt_green)
    val press = colorResource(id=R.color.button_pressed)
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

