package com.tonygnk.entry_scanner.core.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tonygnk.entry_scanner.R
import com.tonygnk.entry_scanner.core.common.utils.findScreenSize
import com.tonygnk.entry_scanner.utills.getExtendedWindowInsets
import kotlinx.coroutines.launch

data class TopBarLazyColumnLeftSide(
    val iconRes: Int = R.drawable.qrcode,
    val contentDescription: String = "",
    val destinationName: String = "Folders",
    val onClick: (() -> Unit)? = {}
)

data class TopBarLazyColumnRightSide(
    val iconRes: Int = R.drawable.qrcode,
    val contentDescription: String = "",
    val onClick: () -> Unit = {}
)

@Composable
fun TopBarLazyColumn(
    modifier: Modifier = Modifier,
    screenName: String = "Notes",
    leftSide: TopBarLazyColumnLeftSide = TopBarLazyColumnLeftSide(),
    rightSide: TopBarLazyColumnRightSide = TopBarLazyColumnRightSide(),
    content: LazyListScope.() -> Unit
) {
    val state = rememberLazyListState()
    val labelIsHiding = remember {
        derivedStateOf {
            state.firstVisibleItemIndex != 0
        }
    }
    val canScrollBackward = remember {
        derivedStateOf {
            state.canScrollBackward
        }
    }
    val textStyle = MaterialTheme.typography.titleMedium.copy(
        fontSize = 18.sp,
        letterSpacing = (-0.35).sp
    )
    val heightOfText = "text".findScreenSize(textStyle).height
    val alpha by animateFloatAsState(
        animationSpec = tween(durationMillis = 300),
        targetValue = if (canScrollBackward.value) 0f else 1f,
        label = "alpha animation"
    )
    val smallColor = when (isSystemInDarkTheme()) {
        true -> MaterialTheme.colorScheme.background
        false -> MaterialTheme.colorScheme.surfaceContainer
    }
    val largeColor = when (isSystemInDarkTheme()) {
        true -> MaterialTheme.colorScheme.background
        false -> MaterialTheme.colorScheme.surfaceContainer
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(smallColor)
            .background(largeColor.copy(alpha))
            .padding(
                getExtendedWindowInsets(14.dp),
            )
    ) {
        Header(
            screenName = screenName,
            leftSide = leftSide,
            listState = state,
            textStyle = textStyle,
            labelIsHiding = labelIsHiding.value,
            rightSide = rightSide,
            heightOfText = heightOfText
        )
        LazyColumn(
            state = state,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 76.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(largeColor)
                .padding(horizontal = 14.dp)
        ) {
            item(
                key = "label",
            ) {
                Text(
                    text = screenName,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    fontWeight = FontWeight.W900,
                    modifier = Modifier
                )
            }
            item(key = "spacing") {
                Spacer(modifier = Modifier.size(8.dp))
            }
            content()
        }
    }
}

@Composable
private fun Header(
    screenName: String,
    leftSide: TopBarLazyColumnLeftSide,
    rightSide: TopBarLazyColumnRightSide,
    listState: LazyListState,
    textStyle: TextStyle,
    labelIsHiding: Boolean,
    heightOfText: Dp,
) {
    val actionColor = MaterialTheme.colorScheme.primary
    val scope = rememberCoroutineScope()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 11.dp,
                bottom = 14.dp,
                end = 14.dp,
            )
    ) {
        if (leftSide.onClick != null) ClickableWithoutRipple(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.weight(1f),
            color = actionColor,
            onClick = leftSide.onClick
        ) { color ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.clip(RoundedCornerShape(30.dp))
            ) {
                Icon(
                    modifier = Modifier.iconSizeFor(heightOfText),
                    painter = painterResource(leftSide.iconRes),
                    contentDescription = leftSide.contentDescription,
                    tint = color,
                )
                Text(
                    text = leftSide.destinationName,
                    style = textStyle.copy(color = color),
                )
            }
        } else Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                modifier = Modifier.iconSizeFor(heightOfText),
                painter = painterResource(leftSide.iconRes),
                contentDescription = leftSide.contentDescription,
                tint = actionColor,
            )
            Text(
                text = leftSide.destinationName,
                style = textStyle.copy(color = actionColor),
            )
        }
        AnimatedVisibility(labelIsHiding) {
            ClickableWithoutRipple(
                contentAlignment = Alignment.CenterEnd,
                color = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            ) { color ->
                Text(
                    text = screenName,
                    textAlign = TextAlign.Center,
                    style = textStyle.copy(color = color),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        ClickableWithoutRipple(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.weight(1f),
            color = actionColor,
            onClick = rightSide.onClick
        ) { color ->
            Icon(
                modifier = Modifier.iconSizeFor(heightOfText),
                tint = color,
                contentDescription = rightSide.contentDescription,
                painter = painterResource(rightSide.iconRes),
            )
        }
    }
}


@Composable
fun ClickableWithoutRipple(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    color: Color,
    onClick: () -> Unit,
    content: @Composable (Color) -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        if (isHovered) 0.3f else 1f, label = "hover"
    )
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isHovered = true
                        if (tryAwaitRelease()) {
                            isHovered = false
                        }
                        if (!tryAwaitRelease()) {
                            isHovered = false
                        }
                    },
                    onTap = {
                        onClick()
                    }
                )
            }
    ) {
        content(color.copy(alpha = alpha))
    }
}

fun Modifier.iconSizeFor(size: Dp) = this.then(size(size - 3.dp))
