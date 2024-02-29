package com.b1nd.dodam.designsystem.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun VectorPreview() {
    Image(Note, null)
}

private var vector: ImageVector? = null

public val Note: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Property 1=note",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 48f,
            viewportHeight = 48f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(43.3218f, 4.46399f)
                curveTo(42.8916f, 4.0964f, 42.3231f, 3.9353f, 41.7626f, 4.0237f)
                lineTo(17.2465f, 7.89469f)
                curveTo(16.3059f, 8.0432f, 15.613f, 8.8541f, 15.613f, 9.8064f)
                verticalLineTo(17.5484f)
                verticalLineTo(29.8064f)
                horizontalLineTo(11.742f)
                curveTo(7.473f, 29.8065f, 4f, 32.9901f, 4f, 36.9032f)
                curveTo(4f, 40.8164f, 7.473f, 44f, 11.742f, 44f)
                curveTo(16.0109f, 44f, 19.4839f, 40.8164f, 19.4839f, 36.9032f)
                verticalLineTo(31.7419f)
                verticalLineTo(19.2022f)
                lineTo(40.1291f, 15.9424f)
                verticalLineTo(26.5806f)
                horizontalLineTo(36.2581f)
                curveTo(31.9892f, 26.5806f, 28.5162f, 29.7642f, 28.5162f, 33.6774f)
                curveTo(28.5162f, 37.5906f, 31.9892f, 40.7742f, 36.2581f, 40.7742f)
                curveTo(40.527f, 40.7742f, 44f, 37.5905f, 44f, 33.6774f)
                verticalLineTo(28.5161f)
                verticalLineTo(13.6774f)
                verticalLineTo(5.93547f)
                curveTo(44f, 5.3695f, 43.7521f, 4.8317f, 43.3218f, 4.464f)
                close()
            }
        }.build()
        return vector!!
    }
