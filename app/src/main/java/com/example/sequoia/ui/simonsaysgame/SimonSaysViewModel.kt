package com.example.sequoia.ui.simonsaysgame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SimonSaysViewModel : ViewModel() {
    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState())
    val viewState : State<ViewState> = _viewState

    data class ViewState (
        // color state for squares
        // 0 - default (green)
        // 1 - correct (purple)
        // 2 - incorrect (red)
        // e.g. [0, 1, 0, ...]
        // have only one non-zero color state at one time ?
        val squareStates: List<Int> = emptyList(),

        // current level
        val score: Int = 0,

        // current sequence
        // each list item is within [0,8] corresponding to each square
        val sequence: List<Int> = emptyList(),

        // current position in sequence
        val sequenceIndex: Int = 0,

        // allow player input if true
        val playerTurn: Boolean = false,

        // remaining number of times player can make errors
        // game over if reaches 0
        val attemptsLeft: Int = 3,

        // indicates if the game has started yet
        val gameRunning: Boolean = false
    )

    private fun emit(state: ViewState) {
        _viewState.value = state
    }
}