package com.b1nd.dodam.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.model.AskStatus
import com.b1nd.dodam.designsystem.theme.DodamTheme

@Composable
fun DodamAskCard(
    askStatus: AskStatus,
    showLabel: Boolean = false,
    startTime: String = "",
    startTimeText: String = "",
    endTime: String = "",
    endTimeText: String = "",
    currentLeftTime: String = "",
    reason: String = "",
    phoneReason: String? = null,
    progress: Float = 0.0f
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        val (statusColor, statusText) = when (askStatus) {
            AskStatus.ALLOWED -> MaterialTheme.colorScheme.primary to "승인됨"
            AskStatus.PENDING -> MaterialTheme.colorScheme.tertiary to "대기중"
            AskStatus.REJECTED -> MaterialTheme.colorScheme.error to "거절됨"
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(100))
                    .background(statusColor)
                    .padding(
                        vertical = 4.dp,
                        horizontal = 12.dp,
                    )
            ) {
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.background,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (!showLabel) {
                Text(
                    text = currentLeftTime,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = reason,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (askStatus != AskStatus.REJECTED) {
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                Row {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                            .align(Alignment.Bottom)
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = currentLeftTime,
                                style = MaterialTheme.typography.titleSmall
                            )
                            if (currentLeftTime.isNotBlank()) {
                                Text(
                                    text = " 남음",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        DodamLinearProgress(progress = progress, color = statusColor)
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = startTimeText,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = startTime,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = endTimeText,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = endTime,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(22.dp))
                phoneReason?.let {
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = "휴대폰 사유",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DodamAskCardPreview() {
    DodamTheme {
        DodamAskCard(
            AskStatus.PENDING,
            reason = "123123",
            progress = 0.5f,
            currentLeftTime = "1시간 25분",
            startTime = "3월 14일",
            startTimeText = "시작",
            endTime = "3월 14일",
            endTimeText = "종료",
            phoneReason = "웹툰보고싶어용 ㅠㅠasdassdaasdasdasdasdasdasdasdasdasdasdasdas"
        )
    }
}
