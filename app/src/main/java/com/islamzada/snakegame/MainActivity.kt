package com.islamzada.snakegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.islamzada.snakegame.ui.theme.DarkGreen
import com.islamzada.snakegame.ui.theme.Shapes
import com.islamzada.snakegame.ui.theme.SnakeGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val game = Game(this, lifecycleScope)

        setContent {
            SnakeGameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Snake(game)
                }
            }
        }
    }
}

@Composable
fun Snake(game: Game) {
    val state by game.state.collectAsState(initial = null)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("SCORE: ${state?.score ?: 0}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black,  modifier = Modifier.padding(top = 25.dp))

        Box(Modifier.weight(1f)) {
            state?.let {
                Board(it)
            }
        }

        Buttons {
            game.move = it
        }
    }
}

@Composable
fun Buttons(onDirectionChange: (Pair<Int, Int>) -> Unit) {
    val buttonSize = Modifier.size(60.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onDirectionChange(Pair(0, -1)) },
                modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen)
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = Color.White)
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onDirectionChange(Pair(-1, 0)) },
                modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = buttonSize)
            Button(
                onClick = { onDirectionChange(Pair(1, 0)) },
                modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.White)
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onDirectionChange(Pair(0, 1)) },
                modifier = buttonSize,
                colors = ButtonDefaults.buttonColors(backgroundColor = DarkGreen)
            ) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.White)
            }
        }
    }
}

@Composable
fun Board(state: State) {
    BoxWithConstraints(Modifier.padding(15.dp)) {

        val tileSize = maxWidth / Game.BOARD_SIZE

        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, DarkGreen)
        )

        Box(
            Modifier
                .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                .size(tileSize)
                .background(DarkGreen, CircleShape)
        )

        state.snake.forEach {
            Box(
                Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(DarkGreen, Shapes.small)
            )
        }

        state.walls.forEach {
            Box(
                Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(Color.Black, Shapes.small)
            )
        }
    }
}