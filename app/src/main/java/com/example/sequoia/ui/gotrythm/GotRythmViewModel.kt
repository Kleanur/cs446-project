package com.example.sequoia.ui.gotrythm

import android.content.Context
import android.os.*
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
import kotlin.math.abs
import kotlin.random.Random

class GotRythmViewModel() : ViewModel() {
    private val _viewState: MutableState<GotRythmViewModel.ViewState> = mutableStateOf(ViewState())
    val viewState : State<GotRythmViewModel.ViewState> = _viewState

    lateinit var vibrate: Vibrator




    private var previousend: Long = 0


    val sequence: MutableList<Long> = mutableListOf()
    val input: MutableList<Long> = mutableListOf()

    var st: Long = 0

    data class ViewState (
        // current level
        val score: Int = 0,
        val counter:Int = 5,
        val attemptsLeft:Int = 3,
        val playerTurn: Boolean = false,
        val gameRunning: Boolean = false
    )

    private fun emit(state: GotRythmViewModel.ViewState) {
        _viewState.value = state
    }



    // initializing the vibration pattern. Vibration pattern starts with off, so add 0 at beginning
    fun init(vib: Vibrator) {
        vibrate = vib
        println(vibrate)
        viewModelScope.launch {
            sequence.add(0)
            sequence.add(Random.nextLong(500, 2000))
            sequence.add(Random.nextLong(500, 2000))
            sequence.add(Random.nextLong(500, 2000))
            val time = timecalc()
            input.add(0)
            vib.vibrate(VibrationEffect.createWaveform(sequence.toLongArray(), -1))
            emit(
                viewState.value.copy(
                    gameRunning = true
                )
            )
            delay(time)
            object : CountDownTimer(4000, 1000) {
                override fun onTick(p0: Long) {
                    val old = viewState.value.counter
                    emit(viewState.value.copy(
                        counter = old - 1
                    ))
                }

                override fun onFinish() {
                    emit(viewState.value.copy(
                        counter = 0,
                        playerTurn = true
                    ))
                }
            }.start()
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
                playerTurn = false,
                counter = 5
            ))
            sequence.add(Random.nextLong(500, 2000))
            sequence.add(Random.nextLong(500, 2000))
            val time = timecalc()
            vib.vibrate(VibrationEffect.createWaveform(sequence.toLongArray(), -1))
            delay(time)
            object : CountDownTimer(4000, 1000) {
                override fun onTick(p0: Long) {
                    val old = viewState.value.counter
                    emit(viewState.value.copy(
                        counter = old - 1
                    ))
                }

                override fun onFinish() {
                    emit(viewState.value.copy(
                        counter = 0,
                        playerTurn = true
                    ))
                }
            }.start()
        }
    }

    fun detresult(): Boolean {
        var average:Long = 0
        for (i in 1 until sequence.size-1) {
            average += ((sequence[i] - abs(sequence[i] - input[i])) *100)/sequence[i]
        }
        return average/sequence.size >= 60
    }

    fun replaysequence() {
        viewModelScope.launch {
            emit(viewState.value.copy(
                playerTurn = false,
                counter = 5
            ))
            val time = timecalc()
            vibrate.vibrate(VibrationEffect.createWaveform(sequence.toLongArray(), -1))
            delay(time)
            object : CountDownTimer(4000, 1000) {
                override fun onTick(p0: Long) {
                    val old = viewState.value.counter
                    emit(
                        viewState.value.copy(
                            counter = old - 1
                        )
                    )
                }

                override fun onFinish() {
                    emit(
                        viewState.value.copy(
                            counter = 0,
                            playerTurn = true
                        )
                    )
                }
            }.start()
        }
    }

    fun recieveinput(endtime: Long) {
        if (input.size == 1) {
            previousend = endtime
        } else {
            input.add(st - previousend)
            previousend = endtime
        }
        input.add(endtime - st)

        if (input.size == sequence.size) {
            val gameresult = detresult()
            //val gameresult = true
            if (gameresult) {
                emit(
                    viewState.value.copy(
                        score = viewState.value.score + 1
                    )
                )
                input.clear()

                startround(vibrate)
            } else {
                emit(
                    viewState.value.copy(
                        attemptsLeft = viewState.value.attemptsLeft - 1
                    )
                )
                if (viewState.value.attemptsLeft != 0) {
                    input.clear()
                    replaysequence()
                } else {
                    // game over
                    emit(
                        viewState.value.copy(
                            playerTurn = false
                        )
                    )
                }
            }
        }
    }

    fun reset() {
        sequence.clear()
        input.clear()
        emit(
            ViewState()
        )
    }


}