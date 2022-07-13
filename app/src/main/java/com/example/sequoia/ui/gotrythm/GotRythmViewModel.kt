package com.example.sequoia.ui.gotrythm

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sequoia.ui.simonsaysgame.SimonSaysViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GotRythmViewModel() : ViewModel() {
    private val _viewState: MutableState<GotRythmViewModel.ViewState> = mutableStateOf(ViewState())
    val viewState : State<GotRythmViewModel.ViewState> = _viewState


    private val timestart = 0
    private val timeend = 0


    val sequence: MutableList<Long> = mutableListOf()
    val input: MutableList<Long> = mutableListOf()

    data class ViewState (
        // current level
        val score: Int = 0,
        val playerTurn: Boolean = false,
        val gameRunning: Boolean = false
    )

    private fun emit(state: GotRythmViewModel.ViewState) {
        _viewState.value = state
    }

    // initializing the vibration pattern. Vibration pattern starts with off, so add 0 at beginning
    fun init(vib: Vibrator) {
        viewModelScope.launch {
            sequence.add(0)
            sequence.add(Random.nextLong(500, 2000))
            sequence.add(Random.nextLong(500, 2000))
            sequence.add(Random.nextLong(500, 2000))
            val time = timecalc()
            input.add(0)
            vib.vibrate(VibrationEffect.createWaveform(sequence.toLongArray(), -1))
            delay(time)
            emit(
                viewState.value.copy(
                    gameRunning = true,
                    playerTurn = true
                )
            )
        }
    }

    fun timecalc(): Long {
        var retval:Long = 0
        for (sec in sequence) {
            retval += sec
        }
        return retval
    }

    fun startround(vib: Vibrator) {
        viewModelScope.launch {
            emit(viewState.value.copy(
                playerTurn = false
            ))
            sequence.add(Random.nextLong(500, 2000))
            sequence.add(Random.nextLong(500, 2000))
            val time = timecalc()
            vib.vibrate(VibrationEffect.createWaveform(sequence.toLongArray(), -1))
            delay(time)
            viewState.value.copy(
                playerTurn = true
            )
        }
    }





}