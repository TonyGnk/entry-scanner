package com.tonygnk.entry_scanner.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.tonygnk.entry_scanner.core.common.utils.findScreenSize


@Composable
fun IconWithTextRow(
    text: String,
    @DrawableRes iconRes: Int,
    contentColor: Color,
    modifier: Modifier = Modifier,
    arrangement: Arrangement.Horizontal,
    style: TextStyle = MaterialTheme.typography.labelLarge.copy(color = contentColor),
    iconLeft: Boolean = true,
    weight: FontWeight = FontWeight.Normal
) {
    when (iconRes) {
        0 -> Text(text = text, style = style, fontWeight = weight, modifier = modifier)

        else -> {
            val size = text.findScreenSize(style)
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = arrangement,
            ) {
                if (!iconLeft) Text(
                    text = text, style = style, fontWeight = weight
                )

                Icon(
                    painter = painterResource(id = iconRes),
                    tint = contentColor,
                    contentDescription = "",
                    modifier = Modifier.iconSizeFor(size.height)
                )
                if (iconLeft) Text(text = text, style = style, fontWeight = weight)
            }
        }
    }
}
