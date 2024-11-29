package com.tonygnk.entry_scanner.core.common.utils

import androidx.compose.ui.graphics.Color

fun averageColor(color1: Color, color2: Color): Color {
    // Extract ARGB components from the two colors
    val alpha1 = color1.alpha
    val red1 = color1.red
    val green1 = color1.green
    val blue1 = color1.blue

    val alpha2 = color2.alpha
    val red2 = color2.red
    val green2 = color2.green
    val blue2 = color2.blue

    // Compute the average of each component
    val avgAlpha = (alpha1 + alpha2) / 2
    val avgRed = (red1 + red2) / 2
    val avgGreen = (green1 + green2) / 2
    val avgBlue = (blue1 + blue2) / 2

    // Combine the averaged components back into a single color
    return Color(avgRed, avgGreen, avgBlue, avgAlpha)
}

operator fun Color.plus(other: Color): Color {
    return Color(
        red = this.red + other.red,
        green = this.green + other.green,
        blue = this.blue + other.blue,
        alpha = this.alpha + other.alpha
    ).copy(
        // Ensure values stay within 0-1 range
        red = (red).coerceIn(0f, 1f),
        green = (green).coerceIn(0f, 1f),
        blue = (blue).coerceIn(0f, 1f),
        alpha = (alpha).coerceIn(0f, 1f)
    )
}
