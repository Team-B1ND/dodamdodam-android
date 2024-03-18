package com.b1nd.dodam.designsystem.component

import android.util.Log
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.animation.NoInteractionSource
import com.b1nd.dodam.designsystem.animation.bounceClick
import com.b1nd.dodam.designsystem.theme.DodamTheme
import kotlin.math.roundToInt

/**
 * DodamToggleButton
 *
 * @param titles DodamToggleButtons tabs title
 * @param onClick called when this button is clicked with index
 * @param startIndex DodamToggleButton start selected index
 * */

@Composable
fun DodamSegmentedButton(titles: List<String>, onClick: (Int) -> Unit, startIndex: Int = 0) {
    var selectedIndex: Int by remember { mutableIntStateOf(startIndex) }
    var selectedXOffset by remember { mutableIntStateOf(12) }
    val x by animateIntAsState(targetValue = selectedXOffset, label = "")
    val boxWidth = LocalConfiguration.current.screenWidthDp / titles.size - 8
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.secondary)) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(x, 0)
                }
                .padding(top = 4.dp, bottom = 4.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(10.dp),
                    )
                    .width(boxWidth.dp)
                    .height(43.dp),
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            titles.forEachIndexed { index, title ->
                val isSelected = selectedIndex == index
                var xOffset by remember { mutableIntStateOf(0) }
                Tab(
                    interactionSource = remember { NoInteractionSource() },
                    selected = isSelected,
                    onClick = {
                        selectedIndex = index
                        selectedXOffset = xOffset
                        onClick(index)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(53.dp)
                        .padding(4.dp)
                        .bounceClick { }
                        .onGloballyPositioned {
                            Log.d(
                                "onGloballyPositioned: xOffset: ",
                                it.positionInParent().x
                                    .roundToInt()
                                    .toString(),
                            )
                            xOffset = it.positionInParent().x.roundToInt()
                        },
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            color = if (isSelected) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.tertiary,
                        )
                    }
                }
            }
        }
    }
}

/**
 * Dodam full width button
 *
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param text the string content in the center of this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.buttonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonElevation.shadowElevation] and [ButtonElevation.tonalElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 */

@Composable
fun DodamFullWidthButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.3f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

/**
 * Dodam large size button
 *
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param text the string content in the center of this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.buttonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonElevation.shadowElevation] and [ButtonElevation.tonalElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 */
@Composable
fun DodamLargeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.3f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

/**
 * Dodam Medium size button
 *
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param text the string content in the center of this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.buttonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonElevation.shadowElevation] and [ButtonElevation.tonalElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 */
@Composable
fun DodamMediumButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.3f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(36.dp),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

/**
 * Dodam Small size button
 *
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param text the string content in the center of this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param shape defines the shape of this button's container, border (when [border] is not null),
 * and shadow (when using [elevation])
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [ButtonDefaults.buttonColors].
 * @param elevation [ButtonElevation] used to resolve the elevation for this button in different
 * states. This controls the size of the shadow below the button. Additionally, when the container
 * color is [ColorScheme.surface], this controls the amount of primary color applied as an overlay.
 * See [ButtonElevation.shadowElevation] and [ButtonElevation.tonalElevation].
 * @param border the border to draw around the container of this button
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 */
@Composable
fun DodamSmallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.3f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(28.dp),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Preview
@Composable
private fun DodamFullWidthButtonPreview() {
    DodamTheme {
        DodamFullWidthButton(
            onClick = {},
            text = "버튼",
        )
    }
}

@Preview
@Composable
private fun DodamLargeButtonPreview() {
    DodamTheme {
        DodamLargeButton(
            onClick = {},
            text = "버튼",
        )
    }
}

@Preview
@Composable
private fun DodamMediumButtonPreview() {
    DodamTheme {
        DodamMediumButton(
            onClick = {},
            text = "버튼",
        )
    }
}

@Preview
@Composable
private fun DodamSmallButtonPreview() {
    DodamTheme {
        DodamSmallButton(
            onClick = {},
            text = "버튼",
        )
    }
}

@Preview
@Composable
private fun DodamSegmentedButtonPreview() {
    val title = listOf("외출", "외박")
    val startIndex = 0
    DodamTheme {
        DodamSegmentedButton(
            titles = title,
            onClick = {},
            startIndex = startIndex,
        )
    }
}
