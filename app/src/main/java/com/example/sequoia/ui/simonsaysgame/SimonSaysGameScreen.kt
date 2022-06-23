package com.example.sequoia.ui.simonsaysgame

import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInteropFilter
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        for (i in 0 until 3) {
            Row {
                for (j in 0 until 3) {
                    squarebutton()
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun squarebutton() {
    val squareColor = colorResource(id = R.color.games_txt_green)
    val presscolor = colorResource(id=R.color.button_pressed)
    
    val color = remember{ mutableStateOf(squareColor)}
    androidx.compose.material3.Button(onClick = { /*TODO*/ },
        shape = RectangleShape,
        modifier = Modifier
            .padding(10.dp)
            .size(100.dp, 100.dp)
            .pointerInteropFilter {
                                  when(it.action) {
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