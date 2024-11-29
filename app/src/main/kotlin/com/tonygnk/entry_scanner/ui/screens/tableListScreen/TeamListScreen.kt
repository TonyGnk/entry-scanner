package com.tonygnk.entry_scanner.ui.screens.tableListScreen

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tonygnk.entry_scanner.R
import com.tonygnk.entry_scanner.ui.AppViewModel
import com.tonygnk.entry_scanner.core.common.utils.findScreenSize
import com.tonygnk.entry_scanner.core.designsystem.components.IconWithTextRow
import com.tonygnk.entry_scanner.data.model.Team
import com.tonygnk.entry_scanner.ui.screens.tableListScreen.other.EmptyTeamList
import kotlinx.coroutines.delay

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
