package com.example.tesifrigo.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Color(0xFF6200EE) /* Replace with your primary color */,
    primaryVariant = Color(0xFF3700B5),
    secondary = Color(0xFF03DAC5)
    /* Other colors like onPrimary, onSecondary, etc. */
)

@Composable
fun ProjectNameTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
***REMOVED***

