package com.example.sequoia.ui.licketysplitgame

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sequoia.R
import com.example.sequoia.route.Routes
import com.example.sequoia.ui.theme.LicketySplitBackgroundColor
import com.example.sequoia.ui.theme.LicketySplitBoltColor
import com.example.sequoia.ui.theme.SequoiaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.log10

val BOLT_SIZE = 50.dp

@Composable
fun LicketySplitGameScreen(nc : NavController) {
    SequoiaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = LicketySplitBackgroundColor
        ) {
            DrawGameScreen(nc)
        }
    }

}

@Composable
fun DrawGameScreen(nc : NavController) {
    val viewModel = viewModel<LicketySplitViewModel>()
    val viewState = viewModel.viewState.value

    val configuration = LocalConfiguration.current

    // boundaries for bolts to appear within
    val canvasHeight = configuration.screenHeightDp.dp - 200.dp
    val canvasWidth = configuration.screenWidthDp.dp - 80.dp

    LaunchedEffect(key1 = Unit) {
        while (isActive) {
            // 25 ticks/second
            delay(40L)
            viewModel.gameTick()
        }
    }

    ConstraintLayout {
        val (gameCanvas, liveScore) = createRefs()

        Row (horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(liveScore) {
                    top.linkTo(parent.top, margin = 20.dp)
                }) {
            Text(
                text = "Chances: ${viewState.attemptsLeft}",
                fontSize = 20.sp
            )
            if (!viewState.gameRunning) {
                Button(onClick ={ viewModel.startGame() } ) {
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
        Box (
            modifier = Modifier
                .width(canvasWidth)
                .height(canvasHeight)
//                .border(BorderStroke(1.dp, Color.Red)) // uncomment to see boundary for bolts to appear
                .constrainAs(gameCanvas) {
                    top.linkTo(liveScore.bottom, margin = 50.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                }
        ) {
            for (bolt in viewState.bolts) {
                IconButton (
                    onClick = { viewModel.clickBolt(bolt.id) },
                    modifier = Modifier
                        .offset(
                            x = calculateXOffset(bolt.x, canvasWidth),
                            y = calculateYOffset(bolt.y, canvasHeight)
                        ).size(BOLT_SIZE)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.lightning),
                        contentDescription = "Play button content description.",
                        tint = LicketySplitBoltColor,
                        modifier = Modifier.size(min(BOLT_SIZE, (29 * log10(bolt.ticks.toDouble())).dp))
                    )
                }
            }
        }
    }

    if (viewState.attemptsLeft <= 0) {
        AlertDialog(onDismissRequest = {},
            title = { Text(text = "Game Completed") },
            text = { Text("Your Score: ${viewState.score}") },
            confirmButton = {
                Button(onClick = { viewModel.reset() }) {
                    Text("Retry?")
                }
            },
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

// calculates offset for bolt based on x% of canvas width
fun calculateXOffset(x: Float, cw: Dp): Dp {
    return min(cw - BOLT_SIZE, cw.times(x))
}

// calculates offset for bolt based on y% of canvas height
fun calculateYOffset(y: Float, ch: Dp): Dp {
    return min(ch - BOLT_SIZE, ch.times(y))
}