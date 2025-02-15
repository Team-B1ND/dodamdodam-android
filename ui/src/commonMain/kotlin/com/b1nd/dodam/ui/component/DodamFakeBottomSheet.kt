package com.b1nd.dodam.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.ui.component.modifier.dropShadow

@Composable
fun DodamFakeBottomSheet(
    modifier: Modifier = Modifier,
    title: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    space: Dp = 4.dp,
    navigationBarPadding: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                blur = 8.dp,
                offsetY = (-4).dp,
                color = DodamTheme.colors.staticBlack.copy(alpha = 0.1f),
            )
            .background(
                color = DodamTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp,
                ),
            ),
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = 24.dp,
                    bottom = 16.dp,
                ),
        ) {
            Box(
                modifier = Modifier
                    .width(64.dp)
                    .height(6.dp)
                    .background(
                        color = DodamTheme.colors.fillAlternative,
                        shape = DodamTheme.shapes.extraSmall,
                    ),
            )
        }
        Column(
            modifier = Modifier
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp,
                ),
        ) {
            title()
            Spacer(modifier = Modifier.height(space))
            content()
            if (navigationBarPadding) {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}
