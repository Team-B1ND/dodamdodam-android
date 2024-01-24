package com.b1nd.dodam.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var _Meal: ImageVector? = null

public val Meal: ImageVector
    get() {
        if (_Meal != null) {
            return _Meal!!
        }
        _Meal = ImageVector.Builder(
            name = "Meal",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
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
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(5f, 29f)
                curveTo(5f, 18.5066f, 13.5066f, 10f, 24f, 10f)
                verticalLineTo(10f)
                curveTo(34.4934f, 10f, 43f, 18.5066f, 43f, 29f)
                verticalLineTo(36f)
                horizontalLineTo(5f)
                verticalLineTo(29f)
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
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(21.3618f, 4.72361f)
                curveTo(21.1956f, 4.3912f, 21.4373f, 4f, 21.809f, 4f)
                horizontalLineTo(26.191f)
                curveTo(26.5627f, 4f, 26.8044f, 4.3912f, 26.6382f, 4.7236f)
                lineTo(25.6382f, 6.72361f)
                curveTo(25.5535f, 6.893f, 25.3804f, 7f, 25.191f, 7f)
                horizontalLineTo(22.809f)
                curveTo(22.6196f, 7f, 22.4465f, 6.893f, 22.3618f, 6.7236f)
                lineTo(21.3618f, 4.72361f)
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
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(4f, 39f)
                verticalLineTo(39.5f)
                curveTo(4f, 41.9853f, 6.0147f, 44f, 8.5f, 44f)
                horizontalLineTo(39.5f)
                curveTo(41.9853f, 44f, 44f, 41.9853f, 44f, 39.5f)
                verticalLineTo(39f)
                horizontalLineTo(4f)
                close()
            }
        }.build()
        return _Meal!!
    }

