package com.tonygnk.entry_scanner.utills

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateInt
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

val LocalAnimatedContentScope = compositionLocalOf<AnimatedContentScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.mySharedElement(
    key: String,
    type: TransitionType = TransitionType.Element,
    skipLookaheadSize: Boolean = false,
    parentCorners: Pair<Int, Int>? = null
): Modifier {
    val modifier = this
    val sharedScope = LocalSharedTransitionScope.current
    val animatedScope = LocalAnimatedContentScope.current

    val roundedCornerAnimation = if (parentCorners != null && animatedScope != null) {
        animatedScope.transition.animateInt(
            label = ""
        ) { enterExit ->
            when (enterExit) {
                EnterExitState.PreEnter -> parentCorners.first
                EnterExitState.Visible -> parentCorners.second
                EnterExitState.PostExit -> parentCorners.second
            }
        }
    } else null

    if (sharedScope != null && animatedScope != null && key.isNotBlank()) {
        with(sharedScope) {
            return when (type) {
                TransitionType.Element -> when (roundedCornerAnimation) {
                    null -> modifier
                        .sharedElement(
                            rememberSharedContentState(key = key),
                            animatedVisibilityScope = animatedScope,
                        )
                        .then(
                            if (skipLookaheadSize) Modifier.skipToLookaheadSize() else Modifier
                        )

                    else -> modifier
                        .sharedElement(
                            rememberSharedContentState(key = key),
                            animatedVisibilityScope = animatedScope,
                            clipInOverlayDuringTransition = OverlayClip(
                                RoundedCornerShape(roundedCornerAnimation.value),
                            )
                        )
                        .then(
                            if (skipLookaheadSize) Modifier.skipToLookaheadSize() else Modifier
                        )
                }

                TransitionType.Bound -> when (roundedCornerAnimation) {
                    null -> modifier
                        .sharedBounds(
                            rememberSharedContentState(key = key),
                            animatedVisibilityScope = animatedScope
                        )
                        .then(
                            if (skipLookaheadSize) Modifier.skipToLookaheadSize() else Modifier
                        )

                    else -> modifier
                        .sharedBounds(
                            rememberSharedContentState(key = key),
                            animatedVisibilityScope = animatedScope,
                            clipInOverlayDuringTransition = OverlayClip(
                                RoundedCornerShape(roundedCornerAnimation.value),
                            )
                        )
                        .then(
                            if (skipLookaheadSize) Modifier.skipToLookaheadSize() else Modifier
                        )
                }
            }

        }
    } else {
        return modifier
    }
}

enum class TransitionType { Element, Bound }
