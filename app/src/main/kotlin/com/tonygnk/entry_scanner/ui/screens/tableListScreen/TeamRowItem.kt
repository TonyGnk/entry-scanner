package com.tonygnk.entry_scanner.ui.screens.tableListScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tonygnk.entry_scanner.R
import com.tonygnk.entry_scanner.core.common.utils.findScreenSize
import com.tonygnk.entry_scanner.data.model.Participant
import com.tonygnk.entry_scanner.data.model.Team

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamRowItem(
    team: Team,
    forceExpanded: Boolean = false,
    setArrived: (String, Int, Boolean) -> Unit = { _, _, _ -> },
) {
    var isExpanded by remember { mutableStateOf(false) }
    var menuOpened by remember { mutableStateOf(false) }


    LaunchedEffect(forceExpanded) {
        isExpanded = forceExpanded
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        menuOpened = true
                    },
                    onTap = {
                        isExpanded = !isExpanded
                    }
                )
            }

            .background(
                color = if (isExpanded) MaterialTheme.colorScheme.surfaceContainerLowest
                else MaterialTheme.colorScheme.surfaceContainer
            )
            .padding(12.dp)
    ) {
        TeamRow(
            team = team,
            isExpanded = isExpanded
        )
        AnimatedVisibility(
            visible = isExpanded
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Spacer(modifier = Modifier.size(2.dp))
                team.members.forEach {
                    MemberRow(it)
                }
            }
        }
    }


    if (menuOpened) BasicAlertDialog(
        onDismissRequest = { menuOpened = false }) {
        DialogColumn(team, setArrived)
    }
}

@Composable
fun DialogColumn(
    team: Team,
    setArrived: (String, Int, Boolean) -> Unit = { _, _, _ -> },
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                MaterialTheme.colorScheme.surfaceContainerLowest
            )
            .padding(12.dp)
    ) {
        DialogText("Team", team.teamName)
        DialogText("Team ID", team.id)
        DialogText("Category", team.category)
        DialogText("Created at", team.createdAt.toDate().toString().split("GMT").first())
        DialogText("Connect us with others", team.autoTeam.toString())
        Spacer(modifier = Modifier.size(4.dp))
        team.members.forEachIndexed { index, participant ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = participant.hasArrived,
                    onCheckedChange = { setArrived(team.id, index, !participant.hasArrived) }
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    DialogText("Name", participant.name)
                    DialogText("Email", participant.email)
                    DialogText("School", participant.school)
                    DialogText("Subject", participant.subject)
                    if (index != team.members.size - 1
                    ) HorizontalDivider()

                }
            }
        }

    }
}

@Composable
fun DialogText(label: String, value: String) {
    Row {
        Text(
            text = "$label:", style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = value, style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
fun TeamRow(
    team: Team,
    isExpanded: Boolean = false,
) {
    val style = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurface
    )
    val heightOfText = team.teamName.findScreenSize(style).height
    val drawable = if (isExpanded) R.drawable.caret_down else R.drawable.caret_right

    val totalMembers = team.members.size
    val arrivedMember = team.members.count { it.hasArrived }
    val membersLabel = "$arrivedMember/$totalMembers"

    Row {
        Icon(
            painter = painterResource(drawable),
            contentDescription = "",
            modifier = Modifier.size(heightOfText),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = team.teamName,
            style = style,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = membersLabel,
            style = style,
        )
    }
}

@Composable
fun MemberRow(
    member: Participant
) {
    val style = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.onSurface
    )
    val heightOfText = member.name.findScreenSize(style).height

    Row {
        Spacer(modifier = Modifier.size(heightOfText))
        Spacer(modifier = Modifier.size(8.dp))
        AnimatedContent(
            member.hasArrived, label = "",
        ) {
            when (it) {
                true -> Row {
                    Icon(
                        painter = painterResource(R.drawable.check_circle),
                        contentDescription = "",
                        modifier = Modifier.size(heightOfText),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }

                false -> {}
            }
        }
        Text(
            style = style,
            text = member.name
        )
    }
}
