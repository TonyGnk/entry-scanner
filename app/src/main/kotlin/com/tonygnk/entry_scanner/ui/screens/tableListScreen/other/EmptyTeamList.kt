package com.tonygnk.entry_scanner.ui.screens.tableListScreen.other

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonygnk.entry_scanner.R
import com.tonygnk.entry_scanner.utills.LottieAnimatedIcon


@Composable
fun EmptyTeamList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.weight(1f))
        LottieAnimatedIcon(
            animationResource = R.raw.zero,
            size = 96
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Teams",
            softWrap = true,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        Spacer(Modifier.weight(1f))
    }
}
