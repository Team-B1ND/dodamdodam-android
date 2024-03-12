package com.b1nd.dodam.designsystem.icons

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun VectorPreview() {
    Image(B1ndLogo, null)
}

private var vector: ImageVector? = null

public val B1ndLogo: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "B1ndlogo",
            defaultWidth = 102.41.dp,
            defaultHeight = 28.34.dp,
            viewportWidth = 102.41f,
            viewportHeight = 28.34f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(7.15f, 22.63f)
                    horizontalLineToRelative(1.62f)
                    curveToRelative(1.85f, 0f, 3.18f, -0.24f, 3.98f, -0.71f)
                    curveToRelative(0.8f, -0.48f, 1.2f, -1.24f, 1.2f, -2.29f)
                    reflectiveCurveToRelative(-0.4f, -1.82f, -1.2f, -2.29f)
                    curveToRelative(-0.8f, -0.48f, -2.13f, -0.71f, -3.98f, -0.71f)
                    horizontalLineToRelative(-1.62f)
                    verticalLineToRelative(6.01f)
                    close()
                    moveToRelative(0f, -11.5f)
                    horizontalLineToRelative(1.35f)
                    curveToRelative(2.3f, 0f, 3.46f, -0.91f, 3.46f, -2.74f)
                    reflectiveCurveToRelative(-1.15f, -2.74f, -3.46f, -2.74f)
                    horizontalLineToRelative(-1.35f)
                    verticalLineToRelative(5.49f)
                    close()
                    moveTo(0f, 0f)
                    horizontalLineToRelative(10.97f)
                    curveTo(13.58f, 0f, 15.55f, 0.64f, 16.91f, 1.89f)
                    curveToRelative(1.35f, 1.25f, 2.03f, 3.06f, 2.03f, 5.41f)
                    curveToRelative(0f, 1.43f, -0.26f, 2.62f, -0.79f, 3.57f)
                    curveToRelative(-0.5f, 0.93f, -1.28f, 1.72f, -2.33f, 2.37f)
                    curveToRelative(1.05f, 0.2f, 1.94f, 0.51f, 2.67f, 0.94f)
                    curveToRelative(0.75f, 0.4f, 1.35f, 0.9f, 1.8f, 1.5f)
                    curveToRelative(0.48f, 0.6f, 0.81f, 1.28f, 1.01f, 2.03f)
                    reflectiveCurveToRelative(0.3f, 1.55f, 0.3f, 2.4f)
                    curveToRelative(0f, 1.33f, -0.24f, 2.5f, -0.71f, 3.53f)
                    curveToRelative(-0.45f, 1.03f, -1.1f, 1.89f, -1.95f, 2.59f)
                    curveToRelative(-0.83f, 0.7f, -1.84f, 1.23f, -3.04f, 1.58f)
                    curveToRelative(-1.2f, 0.35f, -2.55f, 0.53f, -4.06f, 0.53f)
                    horizontalLineTo(0f)
                    verticalLineTo(0f)
                    close()
                    moveToRelative(24.9f, 6.24f)
                    horizontalLineToRelative(-4.13f)
                    verticalLineTo(0f)
                    horizontalLineToRelative(11.5f)
                    verticalLineToRelative(28.33f)
                    horizontalLineToRelative(-7.36f)
                    verticalLineTo(6.25f)
                    close()
                    moveToRelative(10.66f, 22.09f)
                    verticalLineTo(0f)
                    horizontalLineToRelative(7.36f)
                    lineToRelative(13.6f, 17.32f)
                    verticalLineTo(0f)
                    horizontalLineToRelative(7.33f)
                    verticalLineToRelative(28.33f)
                    horizontalLineToRelative(-7.33f)
                    lineToRelative(-13.6f, -17.32f)
                    verticalLineToRelative(17.32f)
                    horizontalLineToRelative(-7.36f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(84.94f, 22.09f)
                    horizontalLineToRelative(1.69f)
                    curveToRelative(1.28f, 0f, 2.42f, -0.2f, 3.42f, -0.6f)
                    curveToRelative(1f, -0.4f, 1.84f, -0.95f, 2.52f, -1.65f)
                    curveToRelative(0.7f, -0.7f, 1.23f, -1.53f, 1.58f, -2.48f)
                    curveToRelative(0.38f, -0.98f, 0.56f, -2.04f, 0.56f, -3.19f)
                    reflectiveCurveToRelative(-0.19f, -2.18f, -0.56f, -3.16f)
                    curveToRelative(-0.38f, -0.98f, -0.91f, -1.82f, -1.62f, -2.52f)
                    curveToRelative(-0.68f, -0.7f, -1.52f, -1.25f, -2.52f, -1.65f)
                    curveToRelative(-1f, -0.4f, -2.13f, -0.6f, -3.38f, -0.6f)
                    horizontalLineToRelative(-1.69f)
                    verticalLineToRelative(15.85f)
                    close()
                    moveTo(77.58f, 0f)
                    horizontalLineToRelative(10.9f)
                    curveToRelative(1.93f, 0f, 3.73f, 0.39f, 5.41f, 1.16f)
                    curveToRelative(1.7f, 0.78f, 3.18f, 1.82f, 4.43f, 3.12f)
                    curveToRelative(1.28f, 1.28f, 2.28f, 2.78f, 3.01f, 4.51f)
                    curveToRelative(0.73f, 1.7f, 1.09f, 3.49f, 1.09f, 5.37f)
                    reflectiveCurveToRelative(-0.36f, 3.64f, -1.09f, 5.37f)
                    curveToRelative(-0.7f, 1.7f, -1.69f, 3.21f, -2.97f, 4.51f)
                    curveToRelative(-1.25f, 1.3f, -2.73f, 2.34f, -4.43f, 3.12f)
                    curveToRelative(-1.68f, 0.78f, -3.49f, 1.16f, -5.45f, 1.16f)
                    horizontalLineToRelative(-10.9f)
                    verticalLineTo(0f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(67.14f, 11.28f)
                    curveToRelative(0f, -0.6f, 0.11f, -1.16f, 0.34f, -1.69f)
                    curveToRelative(0.23f, -0.55f, 0.54f, -1.03f, 0.94f, -1.43f)
                    curveToRelative(0.4f, -0.4f, 0.86f, -0.71f, 1.39f, -0.94f)
                    curveToRelative(0.55f, -0.23f, 1.13f, -0.34f, 1.73f, -0.34f)
                    reflectiveCurveToRelative(1.16f, 0.11f, 1.69f, 0.34f)
                    curveToRelative(0.55f, 0.23f, 1.03f, 0.54f, 1.43f, 0.94f)
                    curveToRelative(0.4f, 0.4f, 0.71f, 0.88f, 0.94f, 1.43f)
                    curveToRelative(0.23f, 0.53f, 0.34f, 1.09f, 0.34f, 1.69f)
                    reflectiveCurveToRelative(-0.11f, 1.18f, -0.34f, 1.73f)
                    curveToRelative(-0.23f, 0.53f, -0.54f, 0.99f, -0.94f, 1.39f)
                    curveToRelative(-0.4f, 0.4f, -0.88f, 0.71f, -1.43f, 0.94f)
                    curveToRelative(-0.53f, 0.23f, -1.09f, 0.34f, -1.69f, 0.34f)
                    reflectiveCurveToRelative(-1.18f, -0.11f, -1.73f, -0.34f)
                    curveToRelative(-0.53f, -0.23f, -0.99f, -0.54f, -1.39f, -0.94f)
                    curveToRelative(-0.4f, -0.4f, -0.71f, -0.86f, -0.94f, -1.39f)
                    curveToRelative(-0.23f, -0.55f, -0.34f, -1.13f, -0.34f, -1.73f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(67.14f, 23.94f)
                    curveToRelative(0f, -0.6f, 0.11f, -1.16f, 0.34f, -1.69f)
                    curveToRelative(0.23f, -0.55f, 0.54f, -1.03f, 0.94f, -1.43f)
                    reflectiveCurveToRelative(0.86f, -0.71f, 1.39f, -0.94f)
                    curveToRelative(0.55f, -0.23f, 1.13f, -0.34f, 1.73f, -0.34f)
                    reflectiveCurveToRelative(1.16f, 0.11f, 1.69f, 0.34f)
                    curveToRelative(0.55f, 0.23f, 1.03f, 0.54f, 1.43f, 0.94f)
                    reflectiveCurveToRelative(0.71f, 0.88f, 0.94f, 1.43f)
                    curveToRelative(0.23f, 0.53f, 0.34f, 1.09f, 0.34f, 1.69f)
                    reflectiveCurveToRelative(-0.11f, 1.18f, -0.34f, 1.73f)
                    curveToRelative(-0.23f, 0.53f, -0.54f, 0.99f, -0.94f, 1.39f)
                    curveToRelative(-0.4f, 0.4f, -0.88f, 0.71f, -1.43f, 0.94f)
                    curveToRelative(-0.53f, 0.23f, -1.09f, 0.34f, -1.69f, 0.34f)
                    reflectiveCurveToRelative(-1.18f, -0.11f, -1.73f, -0.34f)
                    curveToRelative(-0.53f, -0.23f, -0.99f, -0.54f, -1.39f, -0.94f)
                    curveToRelative(-0.4f, -0.4f, -0.71f, -0.86f, -0.94f, -1.39f)
                    curveToRelative(-0.23f, -0.55f, -0.34f, -1.13f, -0.34f, -1.73f)
                    close()
                }
            }
        }.build()
        return vector!!
    }

