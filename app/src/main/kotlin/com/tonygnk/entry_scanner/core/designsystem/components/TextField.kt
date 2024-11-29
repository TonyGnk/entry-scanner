package com.tonygnk.entry_scanner.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DatathonTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    textFieldStyle: TextStyle,
    placeHolder: String,
    hasError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Unspecified,
    iconSize: Dp,
    onKeyboardAction: KeyboardActionHandler,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    @DrawableRes iconRes: Int,
) {
    val keyboardOptions = KeyboardOptions(
        keyboardType = keyboardType,
        imeAction = imeAction,
        autoCorrectEnabled = false,
        capitalization = capitalization,
    )
    BasicTextField(
        textStyle = textFieldStyle,
        modifier = modifier.fillMaxWidth(),
        lineLimits = TextFieldLineLimits.SingleLine,
        state = state,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorator = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = shape
                    )
                    .then(
                        if (hasError) Modifier.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.error,
                            shape = shape
                        ) else Modifier
                    )
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(iconSize),
                    contentDescription = ""
                )
                Spacer(Modifier.width(16.dp))
                Box {
                    if (state.text.toString().isBlank()) Text(
                        text = placeHolder, style = textFieldStyle.copy(
                            color = textFieldStyle.color.copy(alpha = 0.7f)
                        )
                    )
                    it()
                }
            }
        }
    )
}
