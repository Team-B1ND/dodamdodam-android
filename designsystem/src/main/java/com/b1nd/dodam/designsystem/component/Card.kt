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
import com.b1nd.dodam.designsystem.theme.DodamTheme

@Composable
fun DodamAskCard(
    askStatus: String,
    labelText: String? = null,
    startTime: String = "",
    startTimeText: String = "",
    endTime: String = "",
    endTimeText: String = "",
    currentLeftTime: String = "",
    reason: String = "",
    rejectedReason: String? = null,
    phoneReason: String? = null,
    progress: Float = 0.0f,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
    ) {
        val (statusColor, statusText) = when (askStatus) {
            "ALLOWED" -> MaterialTheme.colorScheme.primary to "승인됨"
            "REJECTED" -> MaterialTheme.colorScheme.error to "거절됨"
            else -> MaterialTheme.colorScheme.tertiary to "대기중"
        }
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
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            labelText?.let {
                Text(
                    text = labelText,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = reason,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        if (askStatus != "REJECTED") { // 신청이 승인, 대기중일 때
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Bottom),
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = currentLeftTime,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        if (currentLeftTime.isNotBlank()) {
                            Text(
                                text = " 남음",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    DodamLinearProgress(
                        progress = progress,
                        color = statusColor,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    DodamDescriptionText(
                        descriptionMessage = startTimeText,
                        message = startTime,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DodamDescriptionText(
                        descriptionMessage = endTimeText,
                        message = endTime,
                    )
                }
            }
            phoneReason?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.Top) {
                    DodamDescriptionText(
                        descriptionMessage = "휴대폰 사유",
                        message = phoneReason,
                        alignment = Alignment.Top,
                    )
                }
            }
        } else { // 신청이 거절되었을 때
            rejectedReason?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.height(12.dp))
                DodamDescriptionText(
                    descriptionMessage = "거절사유",
                    message = rejectedReason,
                    alignment = Alignment.Top,
                )
            }
        }
    }
}

@Composable
fun DodamDescriptionText(descriptionMessage: String, message: String, alignment: Alignment.Vertical = Alignment.CenterVertically) {
    Row(verticalAlignment = alignment) {
        Text(
            text = descriptionMessage,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
fun DodamAskCardPendingPreview() {
    DodamTheme {
        Column {
            DodamAskCard(
                askStatus = "PENDING",
                reason = "심야자습을 통해 해군부사관 취업",
                progress = 0.5f,
                currentLeftTime = "11일",
                startTime = "3월 14일",
                startTimeText = "시작",
                endTime = "3월 14일",
                endTimeText = "종료",
                phoneReason = "해군 선배님들의 훈련영상 시청 (웹툰보고싶어용 ㅠㅠ)",
            )
            DodamAskCard(
                askStatus = "ALLOWED",
                reason = "홈푸드 홈푸드 신나는노래",
                progress = 0.5f,
                labelText = "3월 14일",
                currentLeftTime = "30분",
                startTime = "12시 30분",
                startTimeText = "외출",
                endTime = "13시 30분",
                endTimeText = "복귀",
            )
            DodamAskCard(
                askStatus = "REJECTED",
                reason = "크킄 누가 나를 막을테지? 이것은 \"외.박\" 이란 것이다.",
                progress = 0.5f,
                currentLeftTime = "1시간 25분",
                startTime = "3월 14일",
                startTimeText = "시작",
                endTime = "3월 14일",
                endTimeText = "종료",
                rejectedReason = "아앗... 그앞은 나 \"도현욱\"이 지키고 있다.",
            )
        }
    }
}

@Preview
@Composable
fun DodamAskCardAllowedPreview() {
    DodamTheme {
        DodamAskCard(
            askStatus = "ALLOWED",
            reason = "홈푸드 홈푸드 신나는노래",
            progress = 0.5f,
            labelText = "3월 14일",
            currentLeftTime = "30분",
            startTime = "12시 30분",
            startTimeText = "외출",
            endTime = "13시 30분",
            endTimeText = "복귀",
        )
    }
}

@Preview
@Composable
fun DodamAskCardRejectedPreview() {
    DodamTheme {
        DodamAskCard(
            askStatus = "REJECTED",
            reason = "크킄 누가 나를 막을테지? 이것은 \"외.박\" 이란 것이다.",
            progress = 0.5f,
            currentLeftTime = "1시간 25분",
            startTime = "3월 14일",
            startTimeText = "시작",
            endTime = "3월 14일",
            endTimeText = "종료",
            rejectedReason = "아앗... 그앞은 나 \"도현욱\"이 지키고 있다.",
        )
    }
}
