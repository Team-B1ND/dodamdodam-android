package com.b1nd.dodam.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton

@Composable
fun DodamReloadCard(
    onClickReload: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    buttonText: String = "다시 불러오기",
    buttonRole: ButtonRole = ButtonRole.Primary,
    buttonSize: ButtonSize = ButtonSize.Large,
) {
    Surface(
        modifier = modifier,
        color = DodamTheme.colors.backgroundNormal,
        shape = DodamTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = title,
                style = DodamTheme.typography.heading2Bold(),
                color = DodamTheme.colors.labelStrong,
            )
            DodamButton(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                onClick = onClickReload,
                text = buttonText,
                buttonSize = buttonSize,
                buttonRole = buttonRole,
            )
        }
    }
}
