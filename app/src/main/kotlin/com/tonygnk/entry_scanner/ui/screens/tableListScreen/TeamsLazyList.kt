package com.tonygnk.entry_scanner.ui.screens.tableListScreen

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tonygnk.entry_scanner.R
import com.tonygnk.entry_scanner.core.designsystem.components.TopBarLazyColumn
import com.tonygnk.entry_scanner.core.designsystem.components.TopBarLazyColumnLeftSide
import com.tonygnk.entry_scanner.core.designsystem.components.TopBarLazyColumnRightSide

@Composable
fun TeamsLazyList(
    forceExpanded: Boolean,
    expandOrCollapseTeams: () -> Unit = {},
    membersLabel: String,
    content: LazyListScope.() -> Unit,
) {
    val contentDescription = "Create Team"

    TopBarLazyColumn(
        content = content,
        modifier = Modifier,
        screenName = "Teams",
        rightSide = TopBarLazyColumnRightSide(
            contentDescription = contentDescription,
            onClick = expandOrCollapseTeams,
            iconRes = if (forceExpanded) R.drawable.arrows_to_line else R.drawable.arrows_from_line
        ),
        leftSide = TopBarLazyColumnLeftSide(
            contentDescription = "",
            onClick = null,
            iconRes = R.drawable.team_check_alt,
            destinationName = membersLabel
        )
    )
}
