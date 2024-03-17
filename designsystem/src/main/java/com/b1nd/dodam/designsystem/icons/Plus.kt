package com.b1nd.dodam.designsystem.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewPlus() {
    Image(imageVector = Plus, contentDescription = null)
}

private var vector: ImageVector? = null

public val Plus: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = Builder(
            name = "Group 668", defaultWidth = 44.0.dp, defaultHeight = 44.0.dp,
            viewportWidth = 44.0f, viewportHeight = 44.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFBDBDBD)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(11.5f, 21.9999f)
                curveTo(11.5f, 22.7705f, 12.1364f, 23.3989f, 12.8989f, 23.3989f)
                horizontalLineTo(20.6033f)
                verticalLineTo(31.1033f)
                curveTo(20.6033f, 31.8636f, 21.2295f, 32.5f, 21.9999f, 32.5f)
                curveTo(22.7705f, 32.5f, 23.4092f, 31.8636f, 23.4092f, 31.1033f)
                verticalLineTo(23.3989f)
                horizontalLineTo(31.1033f)
                curveTo(31.8636f, 23.3989f, 32.5f, 22.7705f, 32.5f, 21.9999f)
                curveTo(32.5f, 21.2295f, 31.8636f, 20.5908f, 31.1033f, 20.5908f)
                horizontalLineTo(23.4092f)
                verticalLineTo(12.8989f)
                curveTo(23.4092f, 12.1364f, 22.7705f, 11.5f, 21.9999f, 11.5f)
                curveTo(21.2295f, 11.5f, 20.6033f, 12.1364f, 20.6033f, 12.8989f)
                verticalLineTo(20.5908f)
                horizontalLineTo(12.8989f)
                curveTo(12.1364f, 20.5908f, 11.5f, 21.2295f, 11.5f, 21.9999f)
                close()
            }
        }
            .build()
        return vector!!
    }
