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

public val MusicalNote: ImageVector
    get() {
        if (_vector != null) {
            return _vector!!
        }
        _vector = Builder(name = "Musical note", defaultWidth = 20.0.dp, defaultHeight =
                20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF402A32)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(18.3813f, 1.7935f)
                curveTo(18.3813f, 1.4622f, 18.0938f, 1.2122f, 17.7688f, 1.256f)
                lineTo(7.1625f, 2.631f)
                curveTo(6.8938f, 2.6685f, 6.6875f, 2.8997f, 6.6875f, 3.1747f)
                verticalLineTo(11.681f)
                curveTo(6.3187f, 11.556f, 5.9187f, 11.481f, 5.5062f, 11.481f)
                curveTo(3.5f, 11.481f, 1.875f, 13.1122f, 1.875f, 15.1185f)
                curveTo(1.875f, 17.1247f, 3.5f, 18.7497f, 5.5062f, 18.7497f)
                curveTo(7.5125f, 18.7497f, 9.1375f, 17.1247f, 9.1375f, 15.1185f)
                curveTo(9.1375f, 15.0435f, 9.1313f, 14.9622f, 9.1313f, 14.8872f)
                verticalLineTo(6.1997f)
                curveTo(9.1313f, 5.956f, 9.3125f, 5.756f, 9.55f, 5.7247f)
                lineTo(15.3937f, 4.9685f)
                curveTo(15.6812f, 4.931f, 15.9375f, 5.156f, 15.9375f, 5.4435f)
                verticalLineTo(9.5247f)
                curveTo(15.5625f, 9.3935f, 15.1625f, 9.3247f, 14.75f, 9.3247f)
                curveTo(12.7438f, 9.3247f, 11.1187f, 10.9497f, 11.1187f, 12.956f)
                curveTo(11.1187f, 14.9622f, 12.7438f, 16.5872f, 14.75f, 16.5872f)
                curveTo(16.7563f, 16.5872f, 18.3813f, 14.9622f, 18.3813f, 12.956f)
                curveTo(18.3813f, 12.9497f, 18.3813f, 12.9497f, 18.3813f, 12.9435f)
                verticalLineTo(1.7935f)
                close()
            }
        }
        .build()
        return _vector!!
    }

private var _vector: ImageVector? = null
