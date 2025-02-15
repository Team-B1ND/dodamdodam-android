package com.b1nd.dodam.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.animation.rememberBounceIndication
import kotlinx.collections.immutable.ImmutableList

enum class DodamMenuItemColor {
    Negative,
    Normal,
}

data class DodamMenuItem(
    val item: String,
    val color: DodamMenuItemColor,
    val onClickItem: () -> Unit,
)

@Composable
fun DodamMenuDialog(items: ImmutableList<DodamMenuItem>) {
    Surface(
        modifier = Modifier
            .sizeIn(minWidth = 280.dp, maxWidth = 360.dp)
            .width(320.dp),
        color = DodamTheme.colors.backgroundNormal,
        shape = DodamTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier.padding(PaddingValues(18.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items.fastForEach {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberBounceIndication(),
                            onClick = it.onClickItem,
                        )
                        .padding(
                            horizontal = 8.dp,
                        ),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = it.item,
                        style = DodamTheme.typography.headlineMedium(),
                        color = when (it.color) {
                            DodamMenuItemColor.Negative -> DodamTheme.colors.statusNegative
                            DodamMenuItemColor.Normal -> DodamTheme.colors.labelNormal
                        },
                    )
                }
            }
        }
    }
}
