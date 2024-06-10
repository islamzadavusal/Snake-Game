package com.islamzada.snakegame

data class State(
    val food: Pair<Int, Int>,
    val snake: List<Pair<Int, Int>>,
    val score: Int = 0,
    val walls: List<Pair<Int, Int>> = listOf(
        Pair(3, 3), Pair(3, 4), Pair(3, 5),
        Pair(10, 10), Pair(10, 11), Pair(10, 12)
    )
)