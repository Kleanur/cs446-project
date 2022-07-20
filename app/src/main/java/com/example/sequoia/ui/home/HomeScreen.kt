package com.example.sequoia.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.sequoia.ui.theme.*
import com.example.sequoia.ui.theme.SequoiaTheme

@Composable
fun HomeScreen(mainViewModel: HomeViewModel = viewModel(), navController: NavController) {
    SequoiaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ConstraintLayout {
                // Create references for the composables to constrain
                val (treeTmg, historyBtn, infoBtn, gamesBtn, nameHeaderTxt) = createRefs()

                Text(
                    text = "Sequoia", fontSize = 32.sp, fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(nameHeaderTxt) {
                        top.linkTo(parent.top, margin = 48.dp)
                        start.linkTo(parent.start, margin = 135.dp)
                        end.linkTo(parent.end, margin = 48.dp)
                        width = Dimension.matchParent
                    },
                    textAlign = TextAlign.Start,
                )

                Box(
                    modifier = Modifier
                        .constrainAs(gamesBtn) {
                            top.linkTo(nameHeaderTxt.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.Games.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(PlayButtonColor)
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_play_arrow),
                            contentDescription = "Play button content description.",
                            tint = PlayTextColor,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "Play",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = PlayTextColor,
                            textAlign = TextAlign.Start,
                        )
                    }
//
                }

                Box(
                    modifier = Modifier
                        .constrainAs(historyBtn) {
                            top.linkTo(gamesBtn.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.History.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(HistoryButtonColor)
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_account_box),
                            contentDescription = "History button content description.",
                            tint = HistoryTextColor,
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "History",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = HistoryTextColor,
                            textAlign = TextAlign.Start,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .constrainAs(infoBtn) {
                            top.linkTo(historyBtn.bottom, margin = 24.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                        }
                        .height(100.dp)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Routes.InfoScreen.route) {
                                popUpTo(Routes.Home.route)
                            }
                        },
                        Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20),
                        colors = ButtonDefaults.buttonColors(InfoButtonColor)
                    ) {

                        Icon(
                            painter = painterResource(R.drawable.info_button),
                            contentDescription = "Info button content description.",
                            tint = InfoTextColor,
                            modifier = Modifier.size(20.dp)
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxSize(),
                            text = "About Us",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = InfoTextColor,
                            textAlign = TextAlign.Start,
                        )
                    }
                }

                Image(painter = painterResource(R.drawable.tree_home), contentDescription = "Image for Home Screen",  modifier = Modifier
                    .constrainAs(treeTmg) {
                        top.linkTo(infoBtn.bottom, margin = 24.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                    .height(240.dp)
                    .padding(start = 16.dp, end = 16.dp) )

            }
        }
    }
}