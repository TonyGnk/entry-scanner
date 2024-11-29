package com.tonygnk.entry_scanner.ui.screens.tableListScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tonygnk.entry_scanner.data.model.Team

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TeamList(
    modifier: Modifier = Modifier,
    teams: List<Team>,
    onScanQRCode: () -> Unit = {},
    setArrived: (String, Int, Boolean) -> Unit = { _, _, _ -> },
) {


    TeamListContent(
        teams = teams, setArrived = setArrived
    )

    FloatingActionButton(
        onScanQRCode = onScanQRCode
    )

}


@Composable
fun TeamListContent(
    teams: List<Team>,
    setArrived: (String, Int, Boolean) -> Unit = { _, _, _ -> },
) {
    val numberOfMembers = teams.sumOf { it.members.size }
    val numberOfArrivedMembers = teams.sumOf { team -> team.members.count { it.hasArrived } }
    val membersLabel = "$numberOfArrivedMembers/$numberOfMembers members"

    var forceExpanded by remember { mutableStateOf(false) }

    TeamsLazyList(
        forceExpanded = forceExpanded,
        membersLabel = membersLabel,
        expandOrCollapseTeams = { forceExpanded = !forceExpanded },
    ) {
        items(items = teams, key = { it.id }) { team ->
            TeamRowItem(
                team = team,
                forceExpanded = forceExpanded,
                setArrived = setArrived,
            )
        }
    }
}
