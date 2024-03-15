package com.b1nd.dodam.designsystem.component

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.theme.DodamTheme

@Composable
fun DodamCircularProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    strokeWidth: Dp = 10.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    strokeCap: StrokeCap = StrokeCap.Round,
    animDuration: Int = 500,
    animDelay: Int = 0,
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val curPercentage by animateFloatAsState(
        targetValue = if (animationPlayed) progress else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay,
            easing = FastOutLinearInEasing,
        ),
        label = "",
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    CircularProgressIndicator(
        progress = curPercentage,
        modifier = modifier.size(70.dp),
        strokeWidth = strokeWidth,
        color = color,
        backgroundColor = backgroundColor,
        strokeCap = strokeCap,
    )
}

@Composable
fun DodamLinearProgress(
    modifier: Modifier = Modifier,
    progress: Float,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    strokeCap: StrokeCap = StrokeCap.Round,
    animDuration: Int = 500,
    animDelay: Int = 0,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage by animateFloatAsState(
        targetValue = if (animationPlayed) progress else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay,
            easing = FastOutLinearInEasing,
        ),
        label = "",
    )
    LaunchedEffect(Unit) {
        animationPlayed = true
    }
    LinearProgressIndicator(
        progress = curPercentage,
        modifier = modifier
            .fillMaxWidth()
            .height(14.dp),
        color = color,
        backgroundColor = backgroundColor,
        strokeCap = strokeCap,
    )
}

@Preview
@Composable
fun DodamProgressPreview() {
    DodamTheme {
        Column {
            DodamCircularProgress(progress = 1f)
            DodamLinearProgress(progress = 1f)
        }
    }
}
