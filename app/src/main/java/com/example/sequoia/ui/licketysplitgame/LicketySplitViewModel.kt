package com.example.sequoia.ui.licketysplitgame

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.math.max
import kotlin.random.Random

const val DEFAULT_SPAWN_TIME = 70
const val DEFAULT_LIFE_TIME = DEFAULT_SPAWN_TIME + 35
const val MIN_SPAWN_TIME = 25
const val MIN_LIFE_TIME = MIN_SPAWN_TIME + 45

class LicketySplitViewModel : ViewModel() {
    private val _viewState: MutableState<ViewState> = mutableStateOf(ViewState())
    val viewState : State<ViewState> = _viewState

    // track the next id value to assign to a bolt
    private var boltID = 0

    // remaining game ticks until next bolt spawns
    private var remTicks = DEFAULT_SPAWN_TIME

    // number of game ticks between bolt spawns
    private var spawnTime = DEFAULT_SPAWN_TIME

    // lifetime of bolts
    private var lifeTime = DEFAULT_LIFE_TIME

    // Public game state
    data class ViewState (
        // List of bolts currently on screen
        val bolts: List<Bolt> =  emptyList(),

        // current score
        val score: Int = 0,

        // remaining number of times player can miss a bolt
        // game over if reaches 0
        val attemptsLeft: Int = 3,

        // indicates if the game has started yet
        val gameRunning: Boolean = false
    )

    // data for single bolt object
    data class Bolt (
        // unique id on the bolt for lookup
        val id: Int,

        // (x%, y%) coordinates for the bolt
        val x: Float,
        val y: Float,

        // number of ticks until bolt despawns
        var ticks: Int
    )

    /*** Private functions ****/

    // emits state for recomposition
    private fun emit(state: ViewState) {
        _viewState.value = state
    }

    // spawns between 3 and 5 new bolts and increments boltID
    // maximum bolts per spawn increase as score increases
    private fun spawnBolts() {
        val numOfBolts = Random.nextInt(2, 5 + max(0, viewState.value.score.floorDiv(80) - 2))
        for (i in 0..numOfBolts) {
            emit(
                viewState.value.copy(
                    bolts = viewState.value.bolts.plus(Bolt(
                        id = boltID++,
                        x = Random.nextFloat(),
                        y = Random.nextFloat(),
                        ticks = lifeTime
                    ))
                )
            )
        }
    }

    /*** Public Interface ***/

    fun startGame() {
        spawnBolts()
        emit(
            viewState.value.copy(
                gameRunning = true
            )
        )
    }

    // advance the game by one tick
    fun gameTick() {
        if (viewState.value.gameRunning && viewState.value.attemptsLeft > 0) {
            // decrement tick counters
            emit(
                viewState.value.copy(
                    bolts = viewState.value.bolts.map { it.copy(ticks = it.ticks - 1) }
                )
            )
            remTicks -= 1

            // check if any bolts reached the end of their timer
            for (bolt in viewState.value.bolts) {
                if (bolt.ticks <= 0) {
                    emit(
                        viewState.value.copy(
                            bolts = viewState.value.bolts.minus(bolt),
                            attemptsLeft = viewState.value.attemptsLeft - 1
                        )
                    )
                }
            }

            // spawn new bolt
            if (remTicks <= 0) {
                remTicks = spawnTime
                spawnBolts()
            }
        }
    }

    // handler for when user presses a bolt
    fun clickBolt(id: Int) {
        // remove bolt and increment score
        emit(
            viewState.value.copy(
                bolts = viewState.value.bolts.filterNot { it.id == id },
                score = viewState.value.score + 1
            )
        )

        // instantly spawn a new bolt if there are none on screen
        if (viewState.value.bolts.isEmpty()) {
            spawnBolts()
            remTicks = spawnTime
        }

        // modify spawnTime and lifeTime as a function of the score
        spawnTime = max(MIN_SPAWN_TIME, DEFAULT_SPAWN_TIME - (viewState.value.score.floorDiv(4)))
        lifeTime = max(MIN_LIFE_TIME, DEFAULT_LIFE_TIME - (viewState.value.score.floorDiv(4)))
    }

    fun reset() {
        boltID = 0
        remTicks = DEFAULT_SPAWN_TIME
        spawnTime = DEFAULT_SPAWN_TIME
        lifeTime = DEFAULT_LIFE_TIME
        emit(ViewState())
    }
}
