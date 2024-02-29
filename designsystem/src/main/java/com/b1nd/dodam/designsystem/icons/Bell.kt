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
    Image(Bell, null)
}

private var vector: ImageVector? = null

public val Bell: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Bell",
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
                pathFillType = PathFillType.EvenOdd,
            ) {
                moveTo(8.21513f, 19.3293f)
                curveTo(8.2151f, 10.8632f, 15.2823f, 4f, 24f, 4f)
                curveTo(32.7177f, 4f, 39.7848f, 10.8632f, 39.7848f, 19.3294f)
                verticalLineTo(28.7279f)
                lineTo(42.7742f, 32.3779f)
                curveTo(43.3109f, 33.0331f, 42.8304f, 34.0004f, 41.9684f, 34.0004f)
                horizontalLineTo(6.03166f)
                curveTo(5.1696f, 34.0004f, 4.6891f, 33.0331f, 5.2258f, 32.3779f)
                lineTo(8.21513f, 28.7279f)
                verticalLineTo(19.3293f)
                close()
                moveTo(23.5478f, 44f)
                curveTo(19.9581f, 44f, 17.0483f, 41.151f, 17.0482f, 37.6365f)
                horizontalLineTo(30.0474f)
                curveTo(30.0473f, 41.151f, 27.1374f, 44f, 23.5478f, 44f)
                close()
            }
        }.build()
        return vector!!
    }
