package com.tonygnk.entry_scanner.core.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun DatathonSegmentedButton(
    map: Map<String, Boolean>,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    onValueChange: (String) -> Unit = { _ -> },
    paddingValues: PaddingValues = PaddingValues(10.dp),
) {
    val hasRunOnce = remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = shape
            )
            .padding(paddingValues)
    ) {
        map.forEach { (key, value) ->
            val scale = remember { Animatable(1f) }

            LaunchedEffect(value) {
                if (value && hasRunOnce.value) {
                    scale.animateTo(
                        targetValue = 1.05f,
                        animationSpec = spring(
                            dampingRatio = 0.5f,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                    scale.animateTo(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = 0.75f,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                }

                hasRunOnce.value = true
            }

            // Animate background color
            val backgroundColor by animateColorAsState(
                targetValue = if (value) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceContainerHigh,
                animationSpec = spring(dampingRatio = 0.7f),
                label = "backgroundColor"
            )

            // Animate text color
            val textColor by animateColorAsState(
                targetValue = if (value) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface,
                animationSpec = spring(dampingRatio = 0.7f),
                label = "textColor"
            )


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .scale(scale.value)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        color = backgroundColor,
//                        if (value) MaterialTheme.colorScheme.secondary
//                        else MaterialTheme.colorScheme.surfaceContainerHigh,
                        //  shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        onValueChange(key)
                    }

                    .padding(6.dp)
            ) {
                Text(
                    text = key,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = textColor
                    ),
                )
            }
        }
    }
}
