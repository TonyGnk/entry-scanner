package com.tonygnk.entry_scanner.utills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tonygnk.entry_scanner.core.common.utils.findScreenSize

@Composable
fun TextFade(
    text: String,
    backgroundColor: Color,
    textTargetWidth: Dp,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    weight: FontWeight? = null,
    textAlign: TextAlign? = null,
    fontStyle: FontStyle? = null,
) {
    val gradientBrush = Brush.horizontalGradient(
        0f to backgroundColor.copy(alpha = 0.1f),
        1f to backgroundColor.copy(alpha = 1f)
    )
    val textSize = text.findScreenSize(style)
    val remainingWidth = textSize.width - textTargetWidth
    val widthOfFade = if (remainingWidth >= 4.dp) {
        textTargetWidth.div(3)  // "abc".findScreenSize(style).width
    } else 0.dp

    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = modifier,
            style = style,
            //   color = style.color,
            fontWeight = weight,
            //  fontWeight = weight,
            //     size = style.fontSize,

            //    fontSize = ,
            maxLines = 1,
            softWrap = false,
            textAlign = textAlign,
            fontStyle = fontStyle,
        )
        Box(
            modifier = Modifier
                .width(widthOfFade)
                .height(textSize.height)
                .background(gradientBrush)
        )
    }
}
