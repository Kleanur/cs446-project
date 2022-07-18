package com.example.sequoia.ui.licketysplitgame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LicketySplitViewModel : ViewModel() {
    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState())
    val viewState : State<ViewState> = _viewState

    // track the next id value to assign to a bolt
    private var b_id = 0

    // remaining game ticks until next bolt spawns
    private var rem_ticks = 0

    // number of game ticks between bolt spawns
    private var spawn_time = 40

    // Public game state
    data class ViewState (
        // List of bolts currently on screen
        // TODO - create data class for bolts
        val bolts: List<Int> =  emptyList(),

        // current score
        // TODO - create function to scale spawn_time based off score
        val score: Int = 0,

        // remaining number of times player can miss a bolt
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

    /*** Public Interface ***/

    fun startGame() {
        // TODO - spawn initial bolt
        emit(
            viewState.value.copy(
                gameRunning = true
            )
        )
    }

    // advance the game by one tick
    fun gameTick() {
        // TODO - decrement bolt timers
        if (viewState.value.gameRunning) {
            rem_ticks -= 1
            if (rem_ticks <= 0) {
                rem_ticks = spawn_time
                // TODO - spawn new bolt
//                emit(
//                    viewState.value.copy(
//                        score = viewState.value.score + 1
//                    )
//                )
            }
        }
    }

    // handler for when user presses a bolt
    fun clickBolt(id: Int) {
        // TODO - remove bolt with specified id, advance timer to spawn the next bolt (?)
        emit(
            viewState.value.copy(
                score = viewState.value.score + 1
            )
        )
    }

    fun reset() {
        b_id = 0
        rem_ticks = 0
        spawn_time = 40
        emit(ViewState())
    }

}