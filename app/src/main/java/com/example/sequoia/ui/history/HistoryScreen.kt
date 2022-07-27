package com.example.sequoia.ui.history

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sequoia.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.sequoia.repository.ScoreImpl
import com.example.sequoia.ui.theme.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.example.sequoia.repository.gameIds
import com.example.sequoia.route.Routes
import com.example.sequoia.ui.simonsaysgame.saveScore
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation


@Composable
fun HistoryScreen(mainViewModel: HistoryViewModel = viewModel(), navController: NavController) {

    var simonSaysBtn by remember { mutableStateOf(false) }
    var pitchPerfectBtn by remember { mutableStateOf(false) }
    var gotRhythmBtn by remember { mutableStateOf(false) }
    var licketySplitBtn by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false)  }
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

                Column(
                    modifier = Modifier
                        .constrainAs(expandableHistory) {
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
                                colors = ButtonDefaults.buttonColors(PlayButtonColor)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.simonsays_icon),
                                    contentDescription = "Simon Says Icon.",
                                    tint = PlayTextColor,
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = "Simon Says",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PlayTextColor,
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                        AnimatedVisibility(simonSaysBtn) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        shape = RoundedCornerShape(
                                            1
                                        )
                                    )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(PlayButtonColor)
                                        .fillMaxWidth()
                                ) {
                                    LineChartView(data = ScoreImpl.getYValues(gameIds["SimonSays"], LocalContext.current), pointcolor = PlayTextColor, linecolor = PlayTextColor)
                                }
                            }
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
                                colors = ButtonDefaults.buttonColors(InfoButtonColor)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.pitchperfect_icon),
                                    contentDescription = "Pitch Perfect Icon.",
                                    tint = InfoTextColor,
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = "Pitch Perfect",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = InfoTextColor,
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                        AnimatedVisibility(pitchPerfectBtn) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(1))){
                                Column(
                                    modifier = Modifier
                                        .background(InfoButtonColor)
                                        .fillMaxWidth()
                                        .verticalScroll(rememberScrollState())) {
                                    LineChartView(data = ScoreImpl.getYValues(gameIds["PitchPerfect"], LocalContext.current), pointcolor = InfoTextColor, linecolor = InfoTextColor)
                                }
                            }
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
                                colors = ButtonDefaults.buttonColors(HistoryButtonColor)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.gotrhythm_icon),
                                    contentDescription = "Got Rhythm Icon.",
                                    tint = HistoryTextColor,
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = "Got Rhythm",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HistoryTextColor,
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                        AnimatedVisibility(gotRhythmBtn) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(1))){
                                Column(
                                    modifier = Modifier
                                        .background(HistoryButtonColor)
                                        .fillMaxWidth()
                                        .verticalScroll(rememberScrollState())) {
                                    LineChartView(data = ScoreImpl.getYValues(gameIds["GotRhythm"], LocalContext.current), pointcolor = HistoryTextColor, linecolor = HistoryTextColor)
                                }
                            }
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
                                colors = ButtonDefaults.buttonColors(PlayButtonColor)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.licketysplit_icon),
                                    contentDescription = "Lickety Split Icon.",
                                    tint = PlayTextColor,
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    text = "Lickety Split",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PlayTextColor,
                                    textAlign = TextAlign.Start,
                                )
                            }
                        }
                        AnimatedVisibility(licketySplitBtn) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(1))){
                                Column(
                                    modifier = Modifier
                                        .background(PlayButtonColor)
                                        .fillMaxWidth()
                                        .verticalScroll(rememberScrollState())) {
                                    LineChartView(data = ScoreImpl.getYValues(gameIds["LicketySplit"], LocalContext.current), pointcolor = PlayTextColor, linecolor = PlayTextColor)
                                }
                            }
                        }

                        //white space
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                        ) {}


                        //delete history button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        {
                            val context = LocalContext.current
                            Button(
                                onClick = {
                                    openDialog.value = true
                                },
                                Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(deleteHistoryButtonColor)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    text = "Delete Score History",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = deleteHistoryButtonText,
                                    textAlign = TextAlign.Center,
                                )
                            }
                            if(openDialog.value){
                                AlertDialog(onDismissRequest = {},
                                    title = { Text(text = "Delete all history?") },
                                    confirmButton = {
                                        Button(onClick = {
                                            ScoreImpl.deleteAllHistory(context = context)
                                            navController.navigate(Routes.History.route) {
                                                popUpTo(Routes.Home.route)
                                            }
                                        }) {
                                            Text("Confirm")
                                        }
                                    },
                                    dismissButton = {
                                        Button(onClick = {
                                            navController.navigate(Routes.History.route) {
                                                popUpTo(Routes.Home.route)
                                            }
                                        }) {
                                            Text("Back to history")
                                        }
                                    }
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}



@Composable
fun LineChartView(data: List<Int>, linecolor: Color, pointcolor: Color) {
    if(data.isEmpty()){
        Text(text = "No data available.", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = deleteHistoryButtonText)
    }
    else {
        var pointList: MutableList<LineChartData.Point> = mutableListOf()
        pointList.add(LineChartData.Point(0F, "0"))
        for (index in data.indices) {
            pointList.add(LineChartData.Point(data[index].toFloat(), (index+1).toString()))
        }
        println(pointList)
        LineChart(
            lineChartData = LineChartData(
                points = pointList,
                startAtZero = true
            ),
            // Optional properties.
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            animation = simpleChartAnimation(),
            pointDrawer = FilledCircularPointDrawer(color = pointcolor),
            lineDrawer = SolidLineDrawer(color= linecolor),
            xAxisDrawer = SimpleXAxisDrawer(),
            yAxisDrawer = SimpleYAxisDrawer(),
            horizontalOffset = 10f
        )
    }
}


