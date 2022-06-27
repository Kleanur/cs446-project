package com.example.sequoia.ui.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sequoia.route.Routes
import com.example.sequoia.R

import com.example.sequoia.ui.settings.SettingsViewModel
import com.example.sequoia.ui.theme.SequoiaTheme

@Composable
fun SwitchDemo() {

}

@Composable
fun SettingsScreen(navController: NavController) {
    SequoiaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val checkedState = remember { mutableStateOf(true) }
            ConstraintLayout {
                // Create references for the composables to constrain
                val (switch, fontSizeBtn, nameHeaderTxt) = createRefs()

                Text(
                    text = "Settings", fontSize = 32.sp, fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.constrainAs(nameHeaderTxt) {
                        top.linkTo(parent.top, margin = 40.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 48.dp)
                        width = Dimension.matchParent
                    },
                    textAlign = TextAlign.Start,
                )

                Text(
                    modifier = Modifier
                        .constrainAs(fontSizeBtn) {
                            top.linkTo(nameHeaderTxt.bottom, margin = 32.dp)
                            start.linkTo(nameHeaderTxt.start, margin = 16.dp)
                            width = Dimension.wrapContent
                            height = Dimension.wrapContent
                        }.padding(16.dp),
                    text = "Font Size",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.history_button_dark_blue),
                    textAlign = TextAlign.Center,
                )

                Box(modifier = Modifier.constrainAs(switch) {
                    top.linkTo(fontSizeBtn.top)
                    end.linkTo(parent.end)
                    start.linkTo(fontSizeBtn.end)
                    bottom.linkTo(fontSizeBtn.bottom)
                }) {
                    CustomSwitch()
                }
            }
        }
    }
}

@Composable
fun CustomSwitch(
    scale: Float = 2f,
    width: Dp = 80.dp,
    height: Dp = 20.dp,
    strokeWidth: Dp = 1.dp,
    checkedTrackColor: Color = Color(0xFF35898F),
    uncheckedTrackColor: Color = Color(0xFFe0e0e0),
    gapBetweenThumbAndTrackEdge: Dp = 4.dp
) {

    val switchON = remember {
        mutableStateOf(true) // Initially the switch is ON
    }

    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    // To move thumb, we need to calculate the position (along x axis)
    val animatePosition = animateFloatAsState(
        targetValue = if (switchON.value)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
    )

    Canvas(
        modifier = Modifier
            .size(width = width, height = height)
            .scale(scale = scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        // This is called when the user taps on the canvas
                        switchON.value = !switchON.value
                    }
                )
            }
    ) {
        // Track
        drawRoundRect(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx()),
            style = Stroke(width = strokeWidth.toPx())
        )

        // Thumb
        drawCircle(
            color = if (switchON.value) checkedTrackColor else uncheckedTrackColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }

    Spacer(modifier = Modifier.height(18.dp))

    Text(text = if (switchON.value) "Small" else "Large")
}