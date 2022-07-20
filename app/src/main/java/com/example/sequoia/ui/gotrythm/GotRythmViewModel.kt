package com.example.sequoia.ui.gotrythm

import android.content.Context
import android.os.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                sequence.add(0)
                sequence.add(Random.nextLong(500, 1500))
                sequence.add(Random.nextLong(500, 1500))
                sequence.add(Random.nextLong(500, 1500))
                val time = timecalc()
                input.add(0)
                vib.vibrate(VibrationEffect.createWaveform(sequence.toLongArray(), -1))


                emit(
                    viewState.value.copy(
                        gameRunning = true
                    )
                )

                // test purpose button color change
                // to make this code work, please comment out delay(time)
                /*var timing = 0
                for (t in 1 until sequence.size) {
                    if (timing == 0) {
                        println("vibrating")
                        timing = 1
                    } else {
                        println("stopped")
                        timing = 0
                    }
                    delay(sequence[t])
                }*/

                delay(time)
                for (i in 1..5) {
                    if (i == 5) {
                        emit(
                            viewState.value.copy(
                                counter = 0,
                                playerTurn = true
                            )
                        )
                    } else {
                        emit(
                            viewState.value.copy(
                                counter = viewState.value.counter - 1
                            )
                        )
                    }
                    delay(1000)
                }
            }
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
            withContext(Dispatchers.Default) {
                emit(
                    viewState.value.copy(
                        playerTurn = false,
                        counter = 5
                    )
                )
                sequence.add(Random.nextLong(500, 1500))
                sequence.add(Random.nextLong(500, 1500))
                input.add(0)
                val time = timecalc()
                vib.vibrate(VibrationEffect.createWaveform(sequence.toLongArray(), -1))

                // test purpose button color change
                // to make this code work, please comment out delay(time)
                /*var timing = 0
                for (t in 1 until sequence.size) {
                    if (timing == 0) {
                        println("vibrating")
                        timing = 1
                    } else {
                        println("stopped")
                        timing = 0
                    }
                    delay(sequence[t])
                }*/
                delay(time)
                for (i in 1..5) {
                    if (i == 5) {
                        emit(
                            viewState.value.copy(
                                counter = 0,
                                playerTurn = true
                            )
                        )
                    } else {
                        emit(
                            viewState.value.copy(
                                counter = viewState.value.counter - 1
                            )
                        )
                    }
                    delay(1000)
                }
            }
        }
    }

    fun detresult(): Boolean {
        var average:Long = 0
        for (i in 1 until sequence.size) {
            average += ((sequence[i] - abs(sequence[i] - input[i])) *100)/sequence[i]
        }
        return average/(sequence.size-1) >= 70
    }

    fun replaysequence() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                emit(
                    viewState.value.copy(
                        playerTurn = false,
                        counter = 5
                    )
                )
                val time = timecalc()
                vibrate.vibrate(VibrationEffect.createWaveform(sequence.toLongArray(), -1))
                delay(time)
                for (i in 1..5) {
                    if (i == 5) {
                        emit(
                            viewState.value.copy(
                                counter = 0,
                                playerTurn = true
                            )
                        )
                    } else {
                        emit(
                            viewState.value.copy(
                                counter = viewState.value.counter - 1
                            )
                        )
                    }
                    delay(1000)
                }
            }
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
                    input.add(0)
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