package com.example.sequoia.ui.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.sequoia.ui.home.HomeViewModel
import com.example.sequoia.ui.theme.SequoiaTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(mainViewModel: HistoryViewModel = viewModel(), navController: NavController) {

    var simonSaysBtn by remember { mutableStateOf(false) }
    var pitchPerfectBtn by remember { mutableStateOf(false) }
    var gotRhythmBtn by remember { mutableStateOf(false) }
    var licketySplitBtn by remember { mutableStateOf(false) }
    SequoiaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ConstraintLayout {
                // Create references for the composables to constrain
                val (expandableHistory, nameHeaderTxt) = createRefs()


                Text(
                    text = "History", fontSize = 32.sp, fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(nameHeaderTxt) {
                        top.linkTo(parent.top, margin = 48.dp)
                        start.linkTo(parent.start, margin = 135.dp)
                        end.linkTo(parent.end, margin = 48.dp)
                        width = Dimension.matchParent
                    },
                    textAlign = TextAlign.Start,
                )

                Card(
                    modifier = Modifier
                        .constrainAs(expandableHistory){
                            top.linkTo(nameHeaderTxt.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        {
                            Button(
                                onClick = {
                                    simonSaysBtn = !simonSaysBtn
                                    pitchPerfectBtn = false
                                    gotRhythmBtn = false
                                    licketySplitBtn = false
                                },
                                Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.play_button_background_olive_green))
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.simonsays_icon),
                                    contentDescription = "Simon Says Icon.",
                                    tint = colorResource(R.color.play_text_dark_green),
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = "Simon Says",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.play_text_dark_green),
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                        AnimatedVisibility(simonSaysBtn) {
                            Text(text = "\n\nScore goes here\n\n", fontSize = 20.sp)
                        }

                        //white space
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        ) {}

                        //pitch perfect
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        {
                            Button(
                                onClick = {
                                    simonSaysBtn = false
                                    pitchPerfectBtn = !pitchPerfectBtn
                                    gotRhythmBtn = false
                                    licketySplitBtn = false
                                },
                                Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.setting_button_background_light_purple))
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.pitchperfect_icon),
                                    contentDescription = "Pitch Perfect Icon.",
                                    tint = colorResource(R.color.setting_button_dark_purple),
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = "Pitch Perfect",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.setting_button_dark_purple),
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                        AnimatedVisibility(pitchPerfectBtn) {
                            androidx.compose.material3.Text(
                                text = "\n\nScore goes here\n\n",
                                fontSize = 20.sp
                            )
                        }

                        //white space
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        ) {}

                        //Got Rhythm Button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        ) {
                            Button(
                                onClick = {
                                    simonSaysBtn = false
                                    pitchPerfectBtn = false
                                    gotRhythmBtn = !gotRhythmBtn
                                    licketySplitBtn = false
                                },
                                Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.history_button_background_light_blue))
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.gotrhythm_icon),
                                    contentDescription = "Got Rhythm Icon.",
                                    tint = colorResource(R.color.history_button_dark_blue),
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = "Got Rhythm",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.history_button_dark_blue),
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                        AnimatedVisibility(gotRhythmBtn) {
                            androidx.compose.material3.Text(
                                text = "\n\nScore goes here\n\n",
                                fontSize = 20.sp
                            )
                        }

                        //white space
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        ) {}

                        // Lickety Split Button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        {
                            Button(
                                onClick = {
                                    simonSaysBtn = false
                                    pitchPerfectBtn = false
                                    gotRhythmBtn = false
                                    licketySplitBtn = !licketySplitBtn
                                },
                                Modifier.fillMaxSize(),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.play_button_background_olive_green))
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.gotrhythm_icon),
                                    contentDescription = "Lickety Split Icon.",
                                    tint = colorResource(R.color.play_text_dark_green),
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = "Lickety Split",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.play_text_dark_green),
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                        AnimatedVisibility(licketySplitBtn) {
                            androidx.compose.material3.Text(
                                text = "\n\nScore goes here\n\n",
                                fontSize = 20.sp
                            )
                        }
                    }

                }
            }
        }
    }
}

