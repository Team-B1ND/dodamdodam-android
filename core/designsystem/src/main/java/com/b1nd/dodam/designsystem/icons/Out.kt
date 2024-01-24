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

public val Out: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Out",
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
                moveTo(8.5f, 4f)
                curveTo(6.0147f, 4f, 4f, 6.0147f, 4f, 8.5f)
                verticalLineTo(39.5f)
                curveTo(4f, 41.9853f, 6.0147f, 44f, 8.5f, 44f)
                horizontalLineTo(39.5f)
                curveTo(41.9853f, 44f, 44f, 41.9853f, 44f, 39.5f)
                verticalLineTo(8.5f)
                curveTo(44f, 6.0147f, 41.9853f, 4f, 39.5f, 4f)
                horizontalLineTo(8.5f)
                close()
                moveTo(8.5f, 7f)
                curveTo(7.6716f, 7f, 7f, 7.6716f, 7f, 8.5f)
                verticalLineTo(39.5f)
                curveTo(7f, 40.3284f, 7.6716f, 41f, 8.5f, 41f)
                horizontalLineTo(39.5f)
                curveTo(40.3284f, 41f, 41f, 40.3284f, 41f, 39.5f)
                verticalLineTo(8.5f)
                curveTo(41f, 7.6716f, 40.3284f, 7f, 39.5f, 7f)
                horizontalLineTo(8.5f)
                close()
            }
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
                moveTo(32.9261f, 13.6818f)
                lineTo(7f, 6f)
                verticalLineTo(42f)
                horizontalLineTo(34f)
                verticalLineTo(15.12f)
                curveTo(34f, 14.4557f, 33.5631f, 13.8705f, 32.9261f, 13.6818f)
                close()
                moveTo(30f, 30f)
                curveTo(31.1046f, 30f, 32f, 29.1046f, 32f, 28f)
                curveTo(32f, 26.8954f, 31.1046f, 26f, 30f, 26f)
                curveTo(28.8954f, 26f, 28f, 26.8954f, 28f, 28f)
                curveTo(28f, 29.1046f, 28.8954f, 30f, 30f, 30f)
                close()
            }
        }.build()
        return vector!!
    }
