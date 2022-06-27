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

    // toggles the entire board to the specified state
    private fun toggleBoard(state: Int): List<Int> {
        return List(9) {state}
    }

    // start new round with sequence complete indicator
    private fun newRound() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // disable player input
                emit(
                    viewState.value.copy(
                        playerTurn = false
                    )
                )

                // sequence complete indicator
                emit(
                    viewState.value.copy(
                        squareStates = toggleBoard(1)
                    )
                )
                delay(500)
                emit(
                    viewState.value.copy(
                        squareStates = toggleBoard(0)
                    )
                )

                // start the new round
                startRound()
            }
        }
    }

    // replay the sequence with incorrect input indicator
    private fun replaySequence() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // disable player input
                emit(
                    viewState.value.copy(
                        playerTurn = false
                    )
                )

                // incorrect input indicator
                emit(
                    viewState.value.copy(
                        squareStates = toggleBoard(2)
                    )
                )
                delay(500)
                emit(
                    viewState.value.copy(
                        squareStates = toggleBoard(0)
                    )
                )
                delay(250)

                // animate sequence
                for (s in sequence) {
                    delay(500)
                    emit(
                        viewState.value.copy(
                            squareStates = toggleSquare(s, 1)
                        )
                    )
                    delay(500)
                    emit(
                        viewState.value.copy(
                            squareStates = toggleSquare(s, 0)
                        )
                    )
                }

                // enable player input
                emit(
                    viewState.value.copy(
                        playerTurn = true
                    )
                )
            }
        }
    }

    /*** Public Interface ***/

    // starts a new round
    fun startRound() {
        // prepare sequence
        extendSequence()
        sequenceIndex = 0

        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                // disable player input
                emit(
                    viewState.value.copy(
                        gameRunning = true,
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
                    delay(500)
                    emit(
                        viewState.value.copy(
                            squareStates = toggleSquare(s, 0)
                        )
                    )
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

    // indicates input from button with specified id
    fun receiveInput(id: Int) {
        if (viewState.value.playerTurn) {
            if (id == sequence[sequenceIndex]) {
                // correct input
                if (sequenceIndex == sequence.size - 1) {
                    // end of sequence
                    emit(
                        viewState.value.copy(
                            score = viewState.value.score + 1
                        )
                    )
                    newRound()
                } else {
                    // advance sequence
                    sequenceIndex += 1
                }
            } else {
                // incorrect input
                sequenceIndex = 0
                emit(
                    viewState.value.copy(
                        attemptsLeft = viewState.value.attemptsLeft - 1
                    )
                )
                if (viewState.value.attemptsLeft != 0) {
                    replaySequence()
                } else {
                    // game over
                    emit(
                        viewState.value.copy(
                            squareStates = toggleBoard(2),
                            playerTurn = false
                        )
                    )
                }
            }
        }
    }

    fun reset() {
        sequence.clear()
        sequenceIndex = 0
        emit(
            ViewState()
        )
    }
}