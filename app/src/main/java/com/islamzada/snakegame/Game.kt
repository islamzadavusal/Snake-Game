package com.islamzada.snakegame

import android.app.AlertDialog
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

class Game(private val context: Context, private val scope: CoroutineScope) {

    private val mutex = Mutex()
    private val mutableState = MutableStateFlow(State(food = Pair(5, 5), snake = listOf(Pair(7, 7))))
    val state: StateFlow<State> = mutableState

    var move = Pair(1, 0)
        set(value) {
            scope.launch {
                mutex.withLock {
                    field = value
                }
            }
        }

    init {
        scope.launch {
            var snakeLength = 5
            var score = 0

            while (true) {
                delay(150)
                mutableState.update {
                    val newPosition = it.snake.first().let { pos ->
                        mutex.withLock {
                            Pair(
                                (pos.first + move.first + BOARD_SIZE) % BOARD_SIZE,
                                (pos.second + move.second + BOARD_SIZE) % BOARD_SIZE
                            )
                        }
                    }

                    if (newPosition == it.food) {
                        snakeLength++
                        score++
                    }

                    if (it.snake.contains(newPosition) || it.walls.contains(newPosition)) {
                        snakeLength = 4
                        score = 0
                        showGameOverDialog(context)
                    }

                    it.copy(
                        food = if (newPosition == it.food) Pair(
                            Random.nextInt(BOARD_SIZE),
                            Random.nextInt(BOARD_SIZE)
                        ) else it.food,
                        snake = listOf(newPosition) + it.snake.take(snakeLength - 1),
                        score = score
                    )
                }
            }
        }
    }

    private fun showGameOverDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Game Over")
            .setMessage("You hit the wall!")
            .setPositiveButton("Restart") { dialog, _ ->
                dialog.dismiss()
                restartGame()
            }
            .setCancelable(false)
            .show()
    }

    private fun restartGame() {
        mutableState.value = State(food = Pair(5, 5), snake = listOf(Pair(7, 7)))
    }


    companion object {
        const val BOARD_SIZE = 25
    }
}