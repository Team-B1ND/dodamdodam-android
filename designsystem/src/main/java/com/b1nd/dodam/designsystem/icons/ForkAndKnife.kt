package com.b1nd.dodam.designsystem.icons

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
private fun VectorPreview() {
    Image(ForkAndKnife, null)
}

private var vector: ImageVector? = null

public val ForkAndKnife: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
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
                moveTo(15.0755f, 44f)
                curveTo(17.3182f, 44f, 18.7008f, 43.1547f, 18.6753f, 41.7162f)
                lineTo(18.1573f, 22.5132f)
                curveTo(18.1527f, 21.8316f, 18.5115f, 21.4214f, 19.3507f, 21.1659f)
                curveTo(22.8192f, 20.1311f, 24.3473f, 19.0423f, 24.1464f, 15.9088f)
                lineTo(23.4201f, 5.52731f)
                curveTo(23.3601f, 4.7267f, 22.6619f, 4.2664f, 21.55f, 4.2664f)
                curveTo(20.4798f, 4.2664f, 19.8279f, 4.7498f, 19.8372f, 5.5643f)
                lineTo(20.0199f, 15.6393f)
                curveTo(20.029f, 16.2537f, 19.4692f, 16.6327f, 18.625f, 16.6327f)
                curveTo(17.7507f, 16.6327f, 17.1584f, 16.2738f, 17.1538f, 15.6871f)
                lineTo(16.8902f, 5.24395f)
                curveTo(16.8647f, 4.4695f, 16.1828f, 4f, 15.0755f, 4f)
                curveTo(13.9682f, 4f, 13.3072f, 4.4695f, 13.2817f, 5.2439f)
                lineTo(13.0227f, 15.6871f)
                curveTo(13.0181f, 16.2677f, 12.4212f, 16.6327f, 11.5469f, 16.6327f)
                curveTo(10.7027f, 16.6327f, 10.1221f, 16.2537f, 10.1267f, 15.6393f)
                lineTo(10.3185f, 5.56426f)
                curveTo(10.3231f, 4.7498f, 9.6966f, 4.2664f, 8.6056f, 4.2664f)
                curveTo(7.4937f, 4.2664f, 6.7909f, 4.7298f, 6.7309f, 5.5273f)
                lineTo(6.021f, 15.9088f)
                curveTo(5.7991f, 19.0423f, 7.3319f, 20.1311f, 10.805f, 21.1659f)
                curveTo(11.6441f, 21.4214f, 11.9983f, 21.8316f, 11.9937f, 22.5132f)
                lineTo(11.4966f, 41.7162f)
                curveTo(11.462f, 43.1577f, 12.842f, 44f, 15.0755f, 44f)
                close()
                moveTo(35.298f, 29.0051f)
                lineTo(34.8003f, 41.6467f)
                curveTo(34.7285f, 43.1577f, 36.1458f, 44f, 38.3584f, 44f)
                curveTo(40.6265f, 44f, 42f, 43.2133f, 42f, 41.8026f)
                verticalLineTo(5.31988f)
                curveTo(42f, 4.4464f, 41.114f, 4f, 40.1201f, 4f)
                curveTo(39.1304f, 4f, 38.4714f, 4.3481f, 37.6343f, 5.1568f)
                curveTo(33.4828f, 9.0267f, 30.6346f, 16.4944f, 30.6346f, 23.2582f)
                verticalLineTo(24.1618f)
                curveTo(30.6346f, 25.377f, 31.257f, 26.2291f, 32.6711f, 26.8525f)
                lineTo(34.0212f, 27.4633f)
                curveTo(34.9601f, 27.893f, 35.3443f, 28.3462f, 35.298f, 29.0051f)
                close()
            }
        }.build()
        return vector!!
    }

