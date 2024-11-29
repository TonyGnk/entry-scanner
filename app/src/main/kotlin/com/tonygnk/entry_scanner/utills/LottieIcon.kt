package com.tonygnk.entry_scanner.utills

import androidx.annotation.FloatRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty


@Composable
fun LottieAnimatedIcon(
    animationResource: Int,
    size: Int,
) {
    var isAnimationPlaying by remember { mutableStateOf(false) }
    var shouldRestartAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isAnimationPlaying = true
        shouldRestartAnimation = true
    }

    val lottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animationResource)
    )

    val animationProgress by animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isAnimationPlaying,
        restartOnPlay = shouldRestartAnimation,
    )

    LaunchedEffect(animationProgress) {
        if (animationProgress == 1f) {
            isAnimationPlaying = false
            shouldRestartAnimation = false
        }
    }

    val itemColor = MaterialTheme.colorScheme.tertiary
    val animatedItemColor by animateColorAsState(itemColor, label = "itemColorAnimation")


    LottieIconStatic(
        lottieComposition = lottieComposition,
        progress = animationProgress,
        animatedColor = animatedItemColor,
        size = size
    )
}


@Composable
fun LottieIconStatic(
    lottieComposition: LottieComposition?,
    @FloatRange(from = 0.0, to = 1.0) progress: Float,
    size: Int = 160,
    animatedColor: Color = Color.Black
) {
    val colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
        animatedColor.hashCode(),
        BlendModeCompat.SRC_ATOP
    )

    LottieAnimation(
        composition = lottieComposition,
        progress = progress,
        dynamicProperties = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR_FILTER,
                value = colorFilter,
                keyPath = arrayOf("**")
            ),
        ),
        modifier = Modifier.size(size.dp)
    )
}
