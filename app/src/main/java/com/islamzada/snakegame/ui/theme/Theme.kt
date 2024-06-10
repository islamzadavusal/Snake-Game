package com.islamzada.snakegame.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = lightColorScheme(
    primary = DarkGreen,
    secondary = DarkGreen,
    background = LightGreen
)

@Composable
fun SnakeGameTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}