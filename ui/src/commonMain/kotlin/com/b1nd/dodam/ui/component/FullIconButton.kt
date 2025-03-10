package com.b1nd.dodam.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import com.b1nd.dodam.designsystem.foundation.DodamIcons

// DodamconButtonclick의 크기가 작고, 클릭 영역이 좁아 임시로 만들었습니다. 여유 나면 수정해서 dds에 올리겠습니다.
@Composable
fun CustomFullIconButton(
    onClick: () -> Unit,
    icon: DodamIcons,
    modifier: Modifier = Modifier,
    size: IconButtonSize = IconButtonSize.Large,
    type: IconButtonType = IconButtonType.Normal,
    enabled: Boolean = true,
) {
    val color = when (type) {
        IconButtonType.Primary -> IconButtonDefaults.PrimaryIconColor
        IconButtonType.Normal -> IconButtonDefaults.NormalIconColor
        IconButtonType.Strong -> IconButtonDefaults.StrongIconColor
    }

    val iconButtonConfig = when (size) {
        IconButtonSize.Large -> IconButtonConfig(
            size = IconButtonDefaults.LargeIconButtonSize,
            shape = IconButtonDefaults.LargeIconButtonShape,
            interactionSize = IconButtonDefaults.LargeIconButtonInteractionSize,
            iconSize = IconButtonDefaults.LargeIconSize,
        )

        IconButtonSize.Medium -> IconButtonConfig(
            size = IconButtonDefaults.MediumIconButtonSize,
            shape = IconButtonDefaults.MediumIconButtonShape,
            interactionSize = IconButtonDefaults.MediumIconButtonInteractionSize,
            iconSize = IconButtonDefaults.MediumIconSize,
        )

        IconButtonSize.Small -> IconButtonConfig(
            size = IconButtonDefaults.SmallIconButtonSize,
            shape = IconButtonDefaults.SmallIconButtonShape,
            interactionSize = IconButtonDefaults.SmallIconButtonInteractionSize,
            iconSize = IconButtonDefaults.SmallIconSize,
        )
    }

    Box(
        modifier = modifier
            .size(iconButtonConfig.size)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberBounceIndication(iconButtonConfig.shape),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(iconButtonConfig.iconSize),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(iconButtonConfig.iconSize)
                    .align(alignment = Alignment.Center),
                imageVector = icon.value,
                contentDescription = "Icon Button",
                tint = color.takeIf { enabled } ?: color.copy(alpha = 0.2f),
            )
        }
    }
}

@Stable
enum class IconButtonSize {
    Large,
    Medium,
    Small,
}

@Stable
enum class IconButtonType {
    Primary,
    Normal,
    Strong,
}

@Immutable
private data class IconButtonConfig(
    val size: Dp,
    val shape: CornerBasedShape,
    val interactionSize: Dp,
    val iconSize: Dp,
)

private object IconButtonDefaults {
    val PrimaryIconColor @Composable get() = DodamTheme.colors.primaryNormal
    val NormalIconColor @Composable get() = DodamTheme.colors.labelAlternative.copy(alpha = 0.5f)
    val StrongIconColor @Composable get() = DodamTheme.colors.labelStrong

    val LargeIconButtonSize = 48.dp
    val MediumIconButtonSize = 44.dp
    val SmallIconButtonSize = 40.dp

    val LargeIconButtonShape @Composable get() = DodamTheme.shapes.medium
    val MediumIconButtonShape @Composable get() = DodamTheme.shapes.small
    val SmallIconButtonShape @Composable get() = DodamTheme.shapes.extraSmall

    val LargeIconButtonInteractionSize = 40.dp
    val MediumIconButtonInteractionSize = 36.dp
    val SmallIconButtonInteractionSize = 32.dp

    val LargeIconSize = 24.dp
    val MediumIconSize = 20.dp
    val SmallIconSize = 16.dp
}
