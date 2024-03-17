package com.b1nd.dodam.designsystem.icons

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewSmileMoon() {
    Image(imageVector = SmailMoon, contentDescription = null)
}

private var vector: ImageVector? = null

public val SmailMoon: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = Builder(
            name = "Full moon face", defaultWidth = 36.0.dp, defaultHeight =
            36.0.dp, viewportWidth = 36.0f, viewportHeight = 36.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFCD53F)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(33.7489f, 17.9614f)
                curveTo(33.7489f, 26.6595f, 26.6976f, 33.7108f, 17.9995f, 33.7108f)
                curveTo(9.3013f, 33.7108f, 2.25f, 26.6595f, 2.25f, 17.9614f)
                curveTo(2.25f, 9.2632f, 9.3013f, 2.2119f, 17.9995f, 2.2119f)
                curveTo(26.6976f, 2.2119f, 33.7489f, 9.2632f, 33.7489f, 17.9614f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFF9C23C)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(2.2747f, 17.0721f)
                curveTo(2.6721f, 16.944f, 3.0959f, 16.8748f, 3.5359f, 16.8748f)
                curveTo(5.8044f, 16.8748f, 7.6435f, 18.7139f, 7.6435f, 20.9824f)
                curveTo(7.6435f, 23.1139f, 6.0199f, 24.8663f, 3.942f, 25.0702f)
                curveTo(2.8598f, 22.9345f, 2.25f, 20.519f, 2.25f, 17.9611f)
                curveTo(2.25f, 17.6628f, 2.2583f, 17.3663f, 2.2747f, 17.0721f)
                close()
                moveTo(29.4268f, 28.7991f)
                curveTo(27.5369f, 30.791f, 25.1318f, 32.2893f, 22.4249f, 33.0803f)
                curveTo(22.1037f, 32.4947f, 21.921f, 31.8224f, 21.921f, 31.1074f)
                curveTo(21.921f, 28.8389f, 23.76f, 26.9998f, 26.0286f, 26.9998f)
                curveTo(27.4414f, 26.9998f, 28.6876f, 27.7131f, 29.4268f, 28.7991f)
                close()
                moveTo(33.6988f, 19.2271f)
                curveTo(33.5453f, 21.1563f, 33.0442f, 22.9875f, 32.2582f, 24.6582f)
                curveTo(31.0389f, 24.3517f, 30.1362f, 23.2482f, 30.1362f, 21.9338f)
                curveTo(30.1362f, 20.3825f, 31.3938f, 19.1248f, 32.9452f, 19.1248f)
                curveTo(33.2062f, 19.1248f, 33.4589f, 19.1604f, 33.6988f, 19.2271f)
                close()
                moveTo(17.4291f, 8.9998f)
                curveTo(18.9804f, 8.9998f, 20.2381f, 7.7422f, 20.2381f, 6.1908f)
                curveTo(20.2381f, 4.6395f, 18.9804f, 3.3818f, 17.4291f, 3.3818f)
                curveTo(15.8777f, 3.3818f, 14.6201f, 4.6395f, 14.6201f, 6.1908f)
                curveTo(14.6201f, 7.7422f, 15.8777f, 8.9998f, 17.4291f, 8.9998f)
                close()
                moveTo(16.6381f, 25.4416f)
                curveTo(17.8199f, 26.3669f, 18.142f, 27.9292f, 17.3576f, 28.9311f)
                curveTo(16.5732f, 29.933f, 14.9792f, 29.9951f, 13.7974f, 29.0698f)
                curveTo(12.6156f, 28.1445f, 12.2935f, 26.5822f, 13.0779f, 25.5803f)
                curveTo(13.8624f, 24.5784f, 15.4563f, 24.5163f, 16.6381f, 25.4416f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF321B41)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0037f, 9.0449f)
                curveTo(10.4681f, 9.0449f, 9.2137f, 10.2906f, 9.2137f, 11.8349f)
                curveTo(9.2137f, 12.1456f, 8.9618f, 12.3974f, 8.6512f, 12.3974f)
                curveTo(8.3405f, 12.3974f, 8.0887f, 12.1456f, 8.0887f, 11.8349f)
                curveTo(8.0887f, 9.6668f, 9.8492f, 7.9199f, 12.0037f, 7.9199f)
                curveTo(12.3143f, 7.9199f, 12.5662f, 8.1718f, 12.5662f, 8.4824f)
                curveTo(12.5662f, 8.7931f, 12.3143f, 9.0449f, 12.0037f, 9.0449f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF321B41)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(23.1188f, 8.4824f)
                curveTo(23.1188f, 8.1718f, 23.3706f, 7.9199f, 23.6813f, 7.9199f)
                curveTo(25.8357f, 7.9199f, 27.5963f, 9.6668f, 27.5963f, 11.8349f)
                curveTo(27.5963f, 12.1456f, 27.3445f, 12.3974f, 27.0338f, 12.3974f)
                curveTo(26.7231f, 12.3974f, 26.4713f, 12.1456f, 26.4713f, 11.8349f)
                curveTo(26.4713f, 10.2906f, 25.2169f, 9.0449f, 23.6813f, 9.0449f)
                curveTo(23.3706f, 9.0449f, 23.1188f, 8.7931f, 23.1188f, 8.4824f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF321B41)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(11.5966f, 13.9319f)
                curveTo(11.8205f, 13.3615f, 12.3783f, 12.9599f, 13.0387f, 12.9599f)
                curveTo(13.7223f, 12.9599f, 14.3057f, 13.4032f, 14.5098f, 14.0226f)
                curveTo(14.6556f, 14.4652f, 15.1326f, 14.7058f, 15.5752f, 14.56f)
                curveTo(16.0178f, 14.4142f, 16.2584f, 13.9373f, 16.1126f, 13.4947f)
                curveTo(15.6866f, 12.2016f, 14.47f, 11.2724f, 13.0387f, 11.2724f)
                curveTo(11.6741f, 11.2724f, 10.4994f, 12.1083f, 10.0257f, 13.3154f)
                curveTo(9.8555f, 13.7492f, 10.0692f, 14.2389f, 10.503f, 14.4091f)
                curveTo(10.9368f, 14.5793f, 11.4264f, 14.3656f, 11.5966f, 13.9319f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF321B41)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(22.7362f, 12.9599f)
                curveTo(22.0757f, 12.9599f, 21.518f, 13.3615f, 21.2941f, 13.9319f)
                curveTo(21.1239f, 14.3656f, 20.6343f, 14.5793f, 20.2005f, 14.4091f)
                curveTo(19.7667f, 14.2389f, 19.553f, 13.7492f, 19.7232f, 13.3154f)
                curveTo(20.1969f, 12.1083f, 21.3716f, 11.2724f, 22.7362f, 11.2724f)
                curveTo(24.1675f, 11.2724f, 25.3841f, 12.2016f, 25.8101f, 13.4947f)
                curveTo(25.9559f, 13.9373f, 25.7153f, 14.4142f, 25.2727f, 14.56f)
                curveTo(24.8301f, 14.7058f, 24.3531f, 14.4652f, 24.2073f, 14.0226f)
                curveTo(24.0032f, 13.4032f, 23.4198f, 12.9599f, 22.7362f, 12.9599f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF321B41)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(13.4666f, 18.652f)
                curveTo(13.1371f, 18.3225f, 12.6028f, 18.3225f, 12.2733f, 18.652f)
                curveTo(11.9438f, 18.9815f, 11.9438f, 19.5157f, 12.2733f, 19.8452f)
                curveTo(15.3591f, 22.931f, 20.3595f, 22.931f, 23.4453f, 19.8452f)
                curveTo(23.7748f, 19.5157f, 23.7748f, 18.9815f, 23.4453f, 18.652f)
                curveTo(23.1158f, 18.3225f, 22.5816f, 18.3225f, 22.2521f, 18.652f)
                curveTo(19.8253f, 21.0787f, 15.8933f, 21.0787f, 13.4666f, 18.652f)
                close()
            }
        }
            .build()
        return vector!!
    }
