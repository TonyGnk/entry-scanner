package com.tonygnk.entry_scanner.core.designsystem.theme

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

private const val DURATION = 250

data object AppTransition {
    val nativeEnter: () -> EnterTransition = {
        fadeIn(animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 50)) +
                slideInHorizontally(
                    initialOffsetX = { it / 6 },
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 0)
                )
    }

    val nativeEnterPop: () -> EnterTransition? = {
        fadeIn(animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 50)) +
                slideInHorizontally(
                    initialOffsetX = { -it / 6 },
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 0)
                )
    }

    val nativeExit: () -> ExitTransition = {
        fadeOut(animationSpec = tween(DURATION, easing = FastOutSlowInEasing)) +
                slideOutHorizontally(
                    targetOffsetX = { -it / 6 },
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
                )
    }

    val nativeExitPop: () -> ExitTransition = {
        fadeOut(animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 0)) +
                slideOutHorizontally(
                    targetOffsetX = { it / 6 },
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 0)
                )
    }

    val scaleEnter: () -> EnterTransition = {
        fadeIn(animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 50)) +
                scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 0)
                )
    }

    val scaleExit: () -> ExitTransition = {
        fadeOut(animationSpec = tween(DURATION, easing = FastOutSlowInEasing)) +
                scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
                )
    }

    val fadeEnter: () -> EnterTransition = {
        fadeIn(animationSpec = tween(DURATION, easing = FastOutSlowInEasing, delayMillis = 50))
    }

    val fadeExit: () -> ExitTransition = {
        fadeOut(animationSpec = tween(DURATION, easing = FastOutSlowInEasing))
    }
}


val iosEasing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)
