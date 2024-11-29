package com.tonygnk.entry_scanner.ui.screens.tableListScreen

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonygnk.entry_scanner.ui.AppViewModel

@Composable
fun TeamListScreen(
    model: AppViewModel,
    onNavigateToScanner: () -> Unit = {},
) {
    val teams by model.teamsFlow.collectAsStateWithLifecycle()

    LaunchedEffect(teams) {
        Log.d("TeamListScreen", "Teams: $teams")
    }

    AnimatedContent(
        targetState = teams.isEmpty(),
        label = "Team List Screen State"
    ) { currentState ->
        when (currentState) {
            true -> LoadingIndicator()
            false -> TeamList(
                onScanQRCode = onNavigateToScanner,
                teams = teams,
                setArrived = model::setArrived
            )
        }
    }

}


@Composable
private fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}
