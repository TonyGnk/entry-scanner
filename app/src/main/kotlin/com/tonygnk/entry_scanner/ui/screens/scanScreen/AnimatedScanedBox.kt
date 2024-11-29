package com.tonygnk.entry_scanner.ui.screens.scanScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedScannedBox(
    state: ScanState
) {

    val color = when (state) {
        ScanState.SCAN -> MaterialTheme.colorScheme.primary
        ScanState.SCANNED -> MaterialTheme.colorScheme.secondary
        ScanState.BLANK -> MaterialTheme.colorScheme.surfaceContainerLowest
        ScanState.ERROR -> MaterialTheme.colorScheme.error
    }


    if (state != ScanState.BLANK) Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 36.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    color.copy(alpha = 0.2f), RoundedCornerShape(14.dp)
                )
                .border(
                    border = BorderStroke(
                        6.dp, color
                    ), shape = RoundedCornerShape(14.dp)
                )
        ) {

        }
    }
}
