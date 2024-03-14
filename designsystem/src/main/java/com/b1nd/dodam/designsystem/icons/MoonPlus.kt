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
    Image(MoonPlus, null)
}

private var vector: ImageVector? = null

public val MoonPlus: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Moonplus",
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
                moveTo(24.7551f, 44f)
                curveTo(33.3939f, 44f, 40.5399f, 39.5247f, 43.72f, 32.0562f)
                curveTo(44.132f, 31.0834f, 44.0897f, 30.2449f, 43.5909f, 29.7616f)
                curveTo(43.177f, 29.3477f, 42.4037f, 29.293f, 41.5838f, 29.6185f)
                curveTo(39.639f, 30.4116f, 37.3334f, 30.8487f, 34.7497f, 30.8487f)
                curveTo(24.3044f, 30.8487f, 17.4772f, 24.2574f, 17.4772f, 14.1082f)
                curveTo(17.4772f, 11.4381f, 18.0165f, 8.4398f, 18.8625f, 6.7008f)
                curveTo(19.3211f, 5.7447f, 19.3043f, 4.9395f, 18.8732f, 4.4562f)
                curveTo(18.4188f, 3.9265f, 17.5756f, 3.8529f, 16.5209f, 4.2626f)
                curveTo(9.0555f, 7.2119f, 4f, 14.9036f, 4f, 23.5889f)
                curveTo(4f, 35.2647f, 12.655f, 44f, 24.7551f, 44f)
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
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(36.75f, 5.75f)
                curveTo(36.75f, 4.7835f, 35.9665f, 4f, 35f, 4f)
                curveTo(34.0335f, 4f, 33.25f, 4.7835f, 33.25f, 5.75f)
                verticalLineTo(16.25f)
                curveTo(33.25f, 17.2165f, 34.0335f, 18f, 35f, 18f)
                curveTo(35.9665f, 18f, 36.75f, 17.2165f, 36.75f, 16.25f)
                verticalLineTo(5.75f)
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
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(40.25f, 12.75f)
                curveTo(41.2165f, 12.75f, 42f, 11.9665f, 42f, 11f)
                curveTo(42f, 10.0335f, 41.2165f, 9.25f, 40.25f, 9.25f)
                horizontalLineTo(29.75f)
                curveTo(28.7835f, 9.25f, 28f, 10.0335f, 28f, 11f)
                curveTo(28f, 11.9665f, 28.7835f, 12.75f, 29.75f, 12.75f)
                horizontalLineTo(40.25f)
                close()
            }
        }.build()
        return vector!!
    }
