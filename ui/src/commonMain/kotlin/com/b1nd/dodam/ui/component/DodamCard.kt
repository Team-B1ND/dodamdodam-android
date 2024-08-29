package com.b1nd.dodam.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.DodamTheme

@Composable
fun DodamCard(
    modifier: Modifier = Modifier,
    statusText: String,
    statusTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    statusColor: Color = MaterialTheme.colorScheme.primary,
    labelText: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceContainer,
                MaterialTheme.shapes.large,
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(100))
                    .background(statusColor)
                    .padding(
                        vertical = 4.dp,
                        horizontal = 12.dp,
                    ),
            ) {
                Text(
                    text = statusText,
                    color = statusTextColor,
                    style = DodamTheme.typography.body1Bold(),
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = labelText,
                color = DodamTheme.colors.lineNormal,
                style = DodamTheme.typography.body1Bold(),
            )
        }

        content()
    }
}
