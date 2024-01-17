package com.b1nd.dodam.designsystem.theme

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.R

@Composable
fun CheckCircleIcon(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Gray300
) {
    Icon(
        modifier = modifier.size(24.dp),
        imageVector = ImageVector.vectorResource(R.drawable.check_circle),
        contentDescription = contentDescription,
        tint = tint,
    )
}

@Composable
fun CheckIcon(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Gray300
) {
    Icon(
        modifier = modifier.size(24.dp),
        imageVector = ImageVector.vectorResource(R.drawable.check),
        contentDescription = contentDescription,
        tint = tint,
    )
}

@Composable
fun RightArrowIcon(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Gray300
) {
    Icon(
        modifier = modifier.size(24.dp),
        imageVector = ImageVector.vectorResource(R.drawable.right_arrow),
        contentDescription = contentDescription,
        tint = tint,
    )
}
