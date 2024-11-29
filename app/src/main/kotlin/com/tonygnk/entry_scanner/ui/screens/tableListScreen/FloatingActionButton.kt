package com.tonygnk.entry_scanner.ui.screens.tableListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tonygnk.entry_scanner.R
import com.tonygnk.entry_scanner.core.designsystem.components.IconWithTextRow
import com.tonygnk.entry_scanner.core.designsystem.theme.EntryScannerTheme

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    onScanQRCode: () -> Unit = {}
) {
    Box(
        contentAlignment = androidx.compose.ui.Alignment.BottomCenter,
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        ExtendedFloatingActionButton(
            onClick = onScanQRCode,
        ) {
            IconWithTextRow(
                text = "Scan QR Code",
                iconRes = R.drawable.qrcode,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier,
                arrangement = Arrangement.spacedBy(8.dp),
                style = MaterialTheme.typography.titleMedium,
                iconLeft = true,
                weight = FontWeight.W400
            )
        }
    }
}

@Preview
@Composable
private fun Preview() = EntryScannerTheme {
    FloatingActionButton()
}
