package com.b1nd.dodam.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var vector: ImageVector? = null

public val Calendar: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Calendar",
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
                moveTo(13f, 4f)
                curveTo(11.3431f, 4f, 10f, 5.3431f, 10f, 7f)
                verticalLineTo(8f)
                horizontalLineTo(8.5f)
                curveTo(6.0147f, 8f, 4f, 10.0147f, 4f, 12.5f)
                verticalLineTo(16f)
                horizontalLineTo(44f)
                verticalLineTo(12.5f)
                curveTo(44f, 10.0147f, 41.9853f, 8f, 39.5f, 8f)
                horizontalLineTo(38f)
                verticalLineTo(7f)
                curveTo(38f, 5.3431f, 36.6569f, 4f, 35f, 4f)
                curveTo(33.3431f, 4f, 32f, 5.3431f, 32f, 7f)
                verticalLineTo(8f)
                horizontalLineTo(16f)
                verticalLineTo(7f)
                curveTo(16f, 5.3431f, 14.6569f, 4f, 13f, 4f)
                close()
                moveTo(44f, 19f)
                horizontalLineTo(4f)
                verticalLineTo(39.5f)
                curveTo(4f, 41.9853f, 6.0147f, 44f, 8.5f, 44f)
                horizontalLineTo(39.5f)
                curveTo(41.9853f, 44f, 44f, 41.9853f, 44f, 39.5f)
                verticalLineTo(19f)
                close()
                moveTo(8f, 24.5f)
                curveTo(8f, 23.6716f, 8.6716f, 23f, 9.5f, 23f)
                horizontalLineTo(14.5f)
                curveTo(15.3284f, 23f, 16f, 23.6716f, 16f, 24.5f)
                verticalLineTo(25.5f)
                curveTo(16f, 26.3284f, 15.3284f, 27f, 14.5f, 27f)
                horizontalLineTo(9.5f)
                curveTo(8.6716f, 27f, 8f, 26.3284f, 8f, 25.5f)
                verticalLineTo(24.5f)
                close()
                moveTo(19.5f, 23f)
                curveTo(18.6716f, 23f, 18f, 23.6716f, 18f, 24.5f)
                verticalLineTo(25.5f)
                curveTo(18f, 26.3284f, 18.6716f, 27f, 19.5f, 27f)
                horizontalLineTo(24.5f)
                curveTo(25.3284f, 27f, 26f, 26.3284f, 26f, 25.5f)
                verticalLineTo(24.5f)
                curveTo(26f, 23.6716f, 25.3284f, 23f, 24.5f, 23f)
                horizontalLineTo(19.5f)
                close()
                moveTo(28f, 24.5f)
                curveTo(28f, 23.6716f, 28.6716f, 23f, 29.5f, 23f)
                horizontalLineTo(34.5f)
                curveTo(35.3284f, 23f, 36f, 23.6716f, 36f, 24.5f)
                verticalLineTo(25.5f)
                curveTo(36f, 26.3284f, 35.3284f, 27f, 34.5f, 27f)
                horizontalLineTo(29.5f)
                curveTo(28.6716f, 27f, 28f, 26.3284f, 28f, 25.5f)
                verticalLineTo(24.5f)
                close()
                moveTo(9.5f, 29f)
                curveTo(8.6716f, 29f, 8f, 29.6716f, 8f, 30.5f)
                verticalLineTo(31.5f)
                curveTo(8f, 32.3284f, 8.6716f, 33f, 9.5f, 33f)
                horizontalLineTo(14.5f)
                curveTo(15.3284f, 33f, 16f, 32.3284f, 16f, 31.5f)
                verticalLineTo(30.5f)
                curveTo(16f, 29.6716f, 15.3284f, 29f, 14.5f, 29f)
                horizontalLineTo(9.5f)
                close()
            }
        }.build()
        return vector!!
    }
