package com.example.sequoia.ui.simonsaysgame

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
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
    Column(
        modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        for (i in 0 until 3) {
            Row {
                for (j in 0 until 3) {
//                    val isLightSquare = i % 2 == j % 2
//                    val squareColor =
//                        if (isLightSquare) colorResource(id = R.color.games_txt_green) else colorResource(
//                            id = R.color.game_button_background_dark_green
//                        )
//                    Box(
//                        modifier = Modifier
//                            .weight(1f)
//                            .aspectRatio(1f)
//                            .background(color = squareColor)
//                    )
                    val squareColor = colorResource(id = R.color.games_txt_green)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .border(
                                border = BorderStroke(
                                    1.dp,
                                    color = colorResource(id = R.color.black)
                                )
                            )
                            .background(color = squareColor)
                    ) {
                            
                        }


                }
            }
        }
    }
}