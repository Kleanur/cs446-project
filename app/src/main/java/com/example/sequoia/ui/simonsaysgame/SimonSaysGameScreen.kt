package com.example.sequoia.ui.simonsaysgame

import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sequoia.R
import com.example.sequoia.route.Routes
import com.example.sequoia.ui.theme.SequoiaTheme
import com.example.sequoia.ui.repository.ScoreImpl
import com.example.sequoia.ui.theme.gameIds

@Composable
fun SimonSaysGameScreen(nc :NavController) {
    SequoiaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DrawSimonSaysBoard(nc)
        }
    }
}

@Composable
fun DrawSimonSaysBoard(nc : NavController) {
    val viewModel = viewModel<SimonSaysViewModel>()
    val viewState = viewModel.viewState.value
    val squareColor = colorResource(id = R.color.games_txt_green)
    val presscolor = colorResource(id=R.color.button_pressed)
    ConstraintLayout {
        val (gamebuttons, livescore) = createRefs()

        Row (horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(livescore) {
                    top.linkTo(parent.top, margin = 20.dp)
                }) {
            Text(
                text = "Chances: ${viewState.attemptsLeft}"
            )
            if (!viewState.gameRunning) {
                Button(onClick ={viewModel.startRound()} ) {
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
            modifier = Modifier.constrainAs(gamebuttons) {
                top.linkTo(livescore.bottom, margin = 150.dp)
                start.linkTo(parent.start, margin = 135.dp)
                end.linkTo(parent.end, margin = 135.dp)
            }
        ) {

            for (i in 0 until 3) {
                Row {
                    for (j in 0 until 3) {
                        if (viewState.squareStates[i * 3 +j] == 1) {
                            squarebutton(presscolor, viewState.playerTurn, i*3 +j, viewModel)
                        } else if (viewState.squareStates[i * 3 +j] == 2) {
                            squarebutton(Color.Red, viewState.playerTurn, i*3 +j, viewModel)
                        } else {
                            squarebutton(squareColor, viewState.playerTurn, i*3+j, viewModel)
                        }
                    }
                }
            }
        }
    }

    if (viewState.attemptsLeft == 0) {
        AlertDialog(onDismissRequest = {},
        title = {Text(text = "GAME OVER")},
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
        val scoreObj = ScoreImpl()
        gameIds["SimonSays"]?.let {
            scoreObj.addScore(gameId = it,
                gameScore = viewState.score, context = LocalContext.current)
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun squarebutton(color : Color, pt: Boolean, index:Int, viewModel: SimonSaysViewModel) {
    val squareColor = colorResource(id = R.color.games_txt_green)
    val presscolor = colorResource(id=R.color.button_pressed)
    
    val color = remember{ mutableStateOf(color)}
    Button(
        onClick = {}, // blank; overwritten by pointerInteropFilter
        shape = RectangleShape,
        modifier = Modifier
            .padding(10.dp)
            .size(100.dp, 100.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (pt) {
                            color.value = presscolor
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (pt) {
                            color.value = squareColor
                            viewModel.receiveInput(index)
                        }
                    }
                }
                true
            },
        colors = ButtonDefaults.buttonColors(containerColor = color.value)) {

    }
}
