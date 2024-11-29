package com.tonygnk.entry_scanner.core.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.unit.Dp

@Composable
fun String.findScreenSize(textStyle: TextStyle): Size {
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val fontFamilyResolver = createFontFamilyResolver(LocalContext.current)

    val textMeasurer = TextMeasurer(
        defaultFontFamilyResolver = fontFamilyResolver,
        defaultDensity = density,
        defaultLayoutDirection = layoutDirection
    )

    val textLayoutResult: TextLayoutResult = textMeasurer.measure(
        text = AnnotatedString(this),
        style = textStyle
    )

    val textWidth = textLayoutResult.size.width
    val textHeight = textLayoutResult.size.height

    return with(density) {
        Size(textWidth.toDp(), textHeight.toDp())
    }
}

data class Size(val width: Dp, val height: Dp)
