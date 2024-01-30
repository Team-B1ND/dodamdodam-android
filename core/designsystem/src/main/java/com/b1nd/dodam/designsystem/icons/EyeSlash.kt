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
    Image(EyeSlash, null)
}

private var _EyeSlash: ImageVector? = null

public val EyeSlash: ImageVector
    get() {
        if (_EyeSlash != null) {
            return _EyeSlash!!
        }
        _EyeSlash = ImageVector.Builder(
            name = "EyeSlash",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
        ).apply {
            group {
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
                    moveTo(16.5173f, 20.722f)
                    curveTo(16.088f, 21.7238f, 15.8522f, 22.8363f, 15.8522f, 24.013f)
                    curveTo(15.8522f, 28.4755f, 19.4267f, 32.2037f, 23.8976f, 32.2037f)
                    curveTo(25.0274f, 32.2037f, 26.103f, 31.9618f, 27.0772f, 31.5249f)
                    lineTo(31.0567f, 35.5959f)
                    curveTo(28.8899f, 36.3764f, 26.4826f, 36.8439f, 23.8924f, 36.8439f)
                    curveTo(12.1922f, 36.8439f, 4f, 27.0392f, 4f, 24.0103f)
                    curveTo(4f, 22.2712f, 6.6839f, 18.3202f, 11.1818f, 15.2638f)
                    lineTo(16.5173f, 20.722f)
                    close()
                    moveTo(43.7806f, 24.0103f)
                    curveTo(43.7806f, 25.7442f, 41.1503f, 29.6987f, 36.6672f, 32.7584f)
                    lineTo(31.2712f, 27.235f)
                    curveTo(31.6966f, 26.243f, 31.931f, 25.1519f, 31.931f, 24.013f)
                    curveTo(31.931f, 19.4333f, 28.3326f, 15.8223f, 23.8976f, 15.8223f)
                    curveTo(22.7767f, 15.8223f, 21.7121f, 16.0491f, 20.7463f, 16.4617f)
                    lineTo(16.8024f, 12.4246f)
                    curveTo(18.9578f, 11.6474f, 21.3417f, 11.1821f, 23.8924f, 11.1821f)
                    curveTo(35.7425f, 11.1821f, 43.7806f, 20.9719f, 43.7806f, 24.0103f)
                    close()
                    moveTo(24.4865f, 28.8745f)
                    curveTo(24.2944f, 28.9029f, 24.0975f, 28.9154f, 23.8976f, 28.9154f)
                    curveTo(21.2534f, 28.9154f, 19.1116f, 26.7216f, 19.1116f, 24.0103f)
                    curveTo(19.1116f, 23.8085f, 19.1236f, 23.6097f, 19.1503f, 23.4156f)
                    lineTo(24.4865f, 28.8745f)
                    close()
                    moveTo(28.6716f, 24.0103f)
                    curveTo(28.6716f, 24.1908f, 28.6621f, 24.3691f, 28.6423f, 24.5441f)
                    lineTo(23.3757f, 19.1532f)
                    curveTo(23.5468f, 19.1326f, 23.7211f, 19.1229f, 23.8976f, 19.1229f)
                    curveTo(26.5418f, 19.1229f, 28.6716f, 21.3045f, 28.6716f, 24.0103f)
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
                    moveTo(35.209f, 37.2289f)
                    curveTo(35.661f, 37.7069f, 36.3799f, 37.7314f, 36.8559f, 37.2289f)
                    curveTo(37.3291f, 36.7265f, 37.3078f, 36.02f, 36.8559f, 35.5421f)
                    lineTo(12.0746f, 10.1881f)
                    curveTo(11.6347f, 9.7225f, 10.8797f, 9.7225f, 10.4132f, 10.1881f)
                    curveTo(9.9759f, 10.6387f, 9.9759f, 11.427f, 10.4132f, 11.8749f)
                    lineTo(35.209f, 37.2289f)
                    close()
                }
            }
        }.build()
        return _EyeSlash!!
    }
