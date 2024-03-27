package com.b1nd.dodam.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Megaphone: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = Builder(name = "Megaphone", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFFFFB02E)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.6651f, 14.6282f)
                lineTo(14.9472f, 12.874f)
                lineTo(13.8757f, 13.3125f)
                lineTo(14.5945f, 15.0688f)
                lineTo(14.5953f, 15.0709f)
                curveTo(14.6989f, 15.3213f, 14.5801f, 15.5968f, 14.3392f, 15.6937f)
                lineTo(11.273f, 16.9515f)
                lineTo(11.2714f, 16.9522f)
                curveTo(11.021f, 17.0558f, 10.7454f, 16.9369f, 10.6485f, 16.696f)
                lineTo(9.9284f, 14.9365f)
                lineTo(8.857f, 15.375f)
                lineTo(9.5751f, 17.1299f)
                curveTo(9.9162f, 17.9749f, 10.8769f, 18.3678f, 11.7131f, 18.0223f)
                lineTo(11.714f, 18.0219f)
                lineTo(14.7733f, 16.767f)
                curveTo(15.618f, 16.4259f, 16.0108f, 15.4655f, 15.6656f, 14.6294f)
                lineTo(15.6651f, 14.6282f)
                close()
                moveTo(5.0f, 14.6878f)
                lineTo(6.0676f, 16.7371f)
                curveTo(6.4552f, 17.1433f, 6.4364f, 17.7871f, 6.0239f, 18.1683f)
                curveTo(5.6177f, 18.5496f, 4.9739f, 18.5308f, 4.5926f, 18.1246f)
                lineTo(1.5239f, 14.8683f)
                curveTo(1.1427f, 14.4621f, 1.1614f, 13.8183f, 1.5676f, 13.4371f)
                curveTo(1.9739f, 13.0558f, 2.6177f, 13.0746f, 2.9989f, 13.4808f)
                lineTo(5.0f, 14.6878f)
                close()
            }
            path(fill = SolidColor(Color(0xFFF9C23C)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(18.2678f, 9.4243f)
                lineTo(11.049f, 1.768f)
                curveTo(10.2553f, 0.9243f, 8.8615f, 1.143f, 8.3678f, 2.193f)
                lineTo(2.999f, 13.4805f)
                lineTo(6.1053f, 16.7743f)
                lineTo(17.6928f, 12.0805f)
                curveTo(18.7615f, 11.6493f, 19.0615f, 10.268f, 18.2678f, 9.4243f)
                close()
            }
        }
        .build()
        return _vector!!
    }

private var _vector: ImageVector? = null
