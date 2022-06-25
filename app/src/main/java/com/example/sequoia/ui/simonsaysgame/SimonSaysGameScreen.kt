package com.example.sequoia.ui.simonsaysgame

import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sequoia.R
import com.example.sequoia.ui.theme.SequoiaTheme

@Composable
fun SimonSaysGameScreen() {
    SequoiaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DrawSimonSaysBoard()
        }
    }
}

@Composable
fun DrawSimonSaysBoard() {
    val viewModel = viewModel<SimonSaysViewModel>()
    val viewState = viewModel.viewState.value
    ConstraintLayout {
        val (gamebuttons, livescore) = createRefs()

        Row (horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth().padding(8.dp)
                .constrainAs(livescore) {
                    top.linkTo(parent.top, margin = 20.dp)
                }) {
            Text(
                text = "Chances: ${viewState.attemptsLeft}"
            )
            if (!viewState.gameRunning) {
                Button(onClick ={viewModel.startRound(viewState)} ) {
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
                        squarebutton(i+j)
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun squarebutton(index: Int) {
    val squareColor = colorResource(id = R.color.games_txt_green)
    val presscolor = colorResource(id=R.color.button_pressed)
    
    val color = remember{ mutableStateOf(squareColor)}
    val i = index
    Button(onClick = { /*TODO*/ },
        shape = RectangleShape,
        modifier = Modifier
            .padding(10.dp)
            .size(100.dp, 100.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        color.value = presscolor
                    }
                    MotionEvent.ACTION_UP -> {
                        color.value = squareColor
                    }
                }
                true
            },
        colors = ButtonDefaults.buttonColors(containerColor = color.value)) {

    }
}