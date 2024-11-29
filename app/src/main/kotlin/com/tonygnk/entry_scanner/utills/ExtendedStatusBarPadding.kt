package com.tonygnk.entry_scanner.utills

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun Modifier.extendedWindowInsets() = composed {
    this.padding(getExtendedWindowInsets())
}

@Composable
fun getExtendedWindowInsets(
    topPaddingIfNoStatusBar: Dp = 0.dp
): PaddingValues {
    val systemBarPadding = WindowInsets.systemBars.asPaddingValues()
    val top = systemBarPadding.calculateTopPadding()
    val start = systemBarPadding.calculateStartPadding(
        layoutDirection = LayoutDirection.Ltr
    )
    val end = systemBarPadding.calculateEndPadding(
        layoutDirection = LayoutDirection.Ltr
    )
    val bottom = systemBarPadding.calculateBottomPadding()

    return PaddingValues(
        top = if (top > 0.dp) {
            top + 10.dp
        } else topPaddingIfNoStatusBar,
        start = start,
        end = end,
        bottom = bottom
    )
}


fun PaddingValues.add(horizontally: Dp = 0.dp, vertically: Dp = 0.dp): PaddingValues {
    return PaddingValues(
        top = this.calculateTopPadding() + vertically,
        start = this.calculateStartPadding(LayoutDirection.Ltr) + horizontally,
        end = this.calculateEndPadding(LayoutDirection.Ltr) + horizontally,
        bottom = this.calculateBottomPadding() + vertically
    )
}

fun PaddingValues.add(
    top: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp
): PaddingValues {
    return PaddingValues(
        top = this.calculateTopPadding() + top,
        start = this.calculateStartPadding(LayoutDirection.Ltr) + start,
        end = this.calculateEndPadding(LayoutDirection.Ltr) + end,
        bottom = this.calculateBottomPadding() + bottom
    )
}
