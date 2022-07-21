package com.example.sequoia.ui.infoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.sequoia.R
import com.example.sequoia.ui.theme.InfoButtonColor
import com.example.sequoia.ui.theme.InfoScreenBackgroundColor
import com.example.sequoia.ui.theme.InfoScreenTextColor
import com.example.sequoia.ui.theme.SequoiaTheme

@Composable
fun InfoScreen(navController: NavController) {
    SequoiaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(InfoScreenBackgroundColor)
            ) {
                ConstraintLayout {
                    val (
                        titleHeader,
                        header1,
                        desc1,
                        header2,
                        desc2,
                        refHeader,
                        refList
                    ) = createRefs()
                    Text(
                        text = "About Us", fontSize = 32.sp, fontWeight = FontWeight.Bold,
                        color = InfoScreenTextColor,
                        modifier = Modifier.constrainAs(titleHeader) {
                            top.linkTo(parent.top, margin = 30.dp)
                            width = Dimension.matchParent
                        },
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = "Brain training games may help older adults with hearing loss",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = InfoScreenTextColor,
                        modifier = Modifier.constrainAs(header1) {
                            top.linkTo(titleHeader.bottom, margin = 20.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            width = Dimension.matchParent
                        },
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        text = "A small experiment suggests that hearing-impaired adults who play computer games that are designed for audio skill improvement may understand conversations in a noisy room easier.",
                        fontSize = 16.sp,
                        color = InfoScreenTextColor,
                        modifier = Modifier.constrainAs(desc1) {
                            top.linkTo(header1.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            width = Dimension.matchParent
                        },
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        text = "Brain Training Game Improves Executive Functions and Processing Speed in the Elderly",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = InfoScreenTextColor,
                        modifier = Modifier.constrainAs(header2) {
                            top.linkTo(desc1.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            width = Dimension.matchParent
                        },
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        text = "There was an experiment that showed the elderly people's improvement in executive functions and processing speeds even within the short term training, which is conducted by playing brain training game.",
                        fontSize = 16.sp,
                        color = InfoScreenTextColor,
                        modifier = Modifier.constrainAs(desc2) {
                            top.linkTo(header2.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            width = Dimension.matchParent
                        },
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        text = "References",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = InfoScreenTextColor,
                        modifier = Modifier.constrainAs(refHeader) {
                            top.linkTo(desc2.bottom, margin = 16.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            width = Dimension.matchParent
                        },
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        text = "https://www.reuters.com/article/us-health-hearing-brain-training-idUSKBN1D22OZ\nhttps://doi.org/10.1371/journal.pone.0029676",
                        fontSize = 12.sp,
                        color = InfoScreenTextColor,
                        modifier = Modifier.constrainAs(refList) {
                            top.linkTo(refHeader.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            width = Dimension.matchParent
                        },
                        textAlign = TextAlign.Start,
                    )

                }
            }
        }
    }
}