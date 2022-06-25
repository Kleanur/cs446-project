package com.example.sequoia.ui.simonsaysgame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class SimonSaysViewModel : ViewModel() {
    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState())
    val viewState : State<ViewState> = _viewState

    // current sequence
    // each list item is within [0,8] corresponding to each square
    private val sequence: MutableList<Int> = mutableListOf()

    // current position in sequence
    private var sequenceIndex: Int = 0

    // Public game state
    data class ViewState (
        // color state for squares
        // 0 - default (green)
        // 1 - correct (purple)
        // 2 - incorrect (red)
        // e.g. [0, 1, 0, ...]
        // have only one non-zero color state at one time ?
        val squareStates: List<Int> =  List(9) { 0 },

        // current level
        val score: Int = 0,

        // allow player input if true
        val playerTurn: Boolean = false,

        // remaining number of times player can make errors
        // game over if reaches 0
        val attemptsLeft: Int = 3,

        // indicates if the game has started yet
        val gameRunning: Boolean = false
    )

    /*** Private functions ****/

    // emits state for recomposition
    private fun emit(state: ViewState) {
        _viewState.value = state
    }

    // extends the current sequence with a randomly generated integer
    private fun extendSequence(){
        sequence.add(Random.nextInt(0, 8))
    }

    // toggles on square with given id to the specified state
    // toggles off all other squares
    // e.g. id = 3, state = 1 -> [0,0,0,1,0,0,0...]
    private fun toggleSquare(id: Int, state: Int): List<Int> {
        val squares = MutableList(9){0}
        squares[id] = state
        return squares.toList()
    }

    /*** Public Interface ***/

    // starts a new round
    fun startRound() {
        // prepare sequence
        extendSequence()
        sequenceIndex = 0

        // possible coroutine issue?? might refer to different memory location for sequence
        // if that is the case then just put sequence back in ViewState
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                emit(
                    viewState.value.copy(
                        gameRunning = true
                    )
                )

                // disable player input
                emit(
                    viewState.value.copy(
                        playerTurn = false
                    )
                )


                // animate sequence
                for (s in sequence) {
                    delay(500)
                    emit(
                        viewState.value.copy(
                            squareStates = toggleSquare(s, 1)
                        )
                    )
                    println(viewState.value.squareStates)
                    delay(500)
                    emit(
                        viewState.value.copy(
                            squareStates = toggleSquare(s, 0)
                        )
                    )
                    println(viewState.value.squareStates)
                }

                // re-enable player input
                emit(
                    viewState.value.copy(
                        playerTurn = true
                    )
                )
            }
        }
    }


    fun receiveInput(id: Int) {

    }



}