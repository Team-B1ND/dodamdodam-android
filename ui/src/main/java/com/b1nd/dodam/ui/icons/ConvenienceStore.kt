package com.b1nd.dodam.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

private var vector: ImageVector? = null

public val ConvenienceStore: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = Builder(
            name = "vector", defaultWidth = 36.0.dp,
            defaultHeight = 36.0.dp, viewportWidth = 36.0f, viewportHeight = 36.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFFD3D3D3)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(8.9888f, 11.2613f)
                horizontalLineTo(27.0225f)
                curveTo(27.63f, 11.2613f, 28.125f, 10.7663f, 28.1362f, 10.1475f)
                verticalLineTo(4.4887f)
                curveTo(28.1362f, 3.87f, 27.63f, 3.375f, 27.0225f, 3.375f)
                horizontalLineTo(8.9888f)
                curveTo(8.37f, 3.375f, 7.875f, 3.8812f, 7.875f, 4.4887f)
                verticalLineTo(10.1475f)
                curveTo(7.875f, 10.7663f, 8.3813f, 11.2613f, 8.9888f, 11.2613f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFD3D3D3)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(3.375f, 13.5113f)
                horizontalLineTo(32.625f)
                verticalLineTo(31.5113f)
                horizontalLineTo(3.375f)
                verticalLineTo(13.5113f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF533566)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(25.8749f, 5.0625f)
                verticalLineTo(9.585f)
                curveTo(25.8749f, 9.8888f, 25.6274f, 10.1363f, 25.3237f, 10.1363f)
                horizontalLineTo(25.3012f)
                curveTo(24.9974f, 10.1363f, 24.7499f, 9.8888f, 24.7499f, 9.585f)
                verticalLineTo(7.8975f)
                horizontalLineTo(22.4999f)
                verticalLineTo(9.585f)
                curveTo(22.4999f, 9.8888f, 22.2524f, 10.1363f, 21.9487f, 10.1363f)
                horizontalLineTo(21.9262f)
                curveTo(21.6224f, 10.1363f, 21.3749f, 9.8888f, 21.3749f, 9.585f)
                verticalLineTo(5.0625f)
                curveTo(21.3749f, 4.7587f, 21.6224f, 4.5113f, 21.9262f, 4.5113f)
                horizontalLineTo(21.9487f)
                curveTo(22.2524f, 4.5113f, 22.4999f, 4.7587f, 22.4999f, 5.0625f)
                verticalLineTo(6.7613f)
                horizontalLineTo(24.7499f)
                verticalLineTo(5.0625f)
                curveTo(24.7499f, 4.7587f, 24.9974f, 4.5113f, 25.3012f, 4.5113f)
                horizontalLineTo(25.3237f)
                curveTo(25.6274f, 4.5113f, 25.8749f, 4.7587f, 25.8749f, 5.0625f)
                close()
                moveTo(13.6574f, 10.1363f)
                curveTo(14.0512f, 10.1363f, 14.2537f, 9.9338f, 14.2537f, 9.54f)
                curveTo(14.2537f, 9.1462f, 14.0399f, 9.0f, 13.6462f, 9.0f)
                horizontalLineTo(12.0824f)
                lineTo(12.9937f, 8.0662f)
                curveTo(13.3537f, 7.7175f, 13.6012f, 7.3913f, 13.7587f, 7.0763f)
                curveTo(13.9049f, 6.7725f, 13.9837f, 6.4575f, 13.9837f, 6.12f)
                curveTo(13.9837f, 5.5912f, 13.8149f, 5.1975f, 13.4774f, 4.9163f)
                curveTo(13.1399f, 4.635f, 12.6449f, 4.5f, 12.0149f, 4.5f)
                curveTo(11.7562f, 4.5f, 11.4862f, 4.5338f, 11.2274f, 4.59f)
                curveTo(10.9687f, 4.6462f, 10.7212f, 4.7475f, 10.4737f, 4.8713f)
                curveTo(10.3387f, 4.9387f, 10.2374f, 5.04f, 10.1924f, 5.1525f)
                curveTo(10.1362f, 5.265f, 10.1137f, 5.31f, 10.1362f, 5.4225f)
                curveTo(10.1587f, 5.535f, 10.2037f, 5.6363f, 10.2712f, 5.7263f)
                curveTo(10.3387f, 5.8162f, 10.4399f, 5.8725f, 10.5524f, 5.9063f)
                curveTo(10.6649f, 5.94f, 10.7999f, 5.9175f, 10.9349f, 5.85f)
                curveTo(11.0924f, 5.76f, 11.2387f, 5.7038f, 11.3849f, 5.67f)
                curveTo(11.5312f, 5.6363f, 11.6662f, 5.6137f, 11.7899f, 5.6137f)
                curveTo(12.2962f, 5.6137f, 12.5662f, 5.8837f, 12.5662f, 6.2775f)
                curveTo(12.5662f, 6.435f, 12.5212f, 6.5925f, 12.4312f, 6.75f)
                curveTo(12.3412f, 6.9075f, 12.1949f, 7.0988f, 11.9924f, 7.3125f)
                lineTo(10.5974f, 8.7975f)
                curveTo(10.4849f, 8.91f, 10.4062f, 9.0225f, 10.3387f, 9.135f)
                curveTo(10.2712f, 9.2475f, 10.2599f, 9.3375f, 10.2599f, 9.4838f)
                curveTo(10.2599f, 9.6975f, 10.3162f, 9.855f, 10.4174f, 9.9563f)
                curveTo(10.5187f, 10.0688f, 10.6874f, 10.1138f, 10.9124f, 10.1138f)
                horizontalLineTo(13.6574f)
                verticalLineTo(10.1363f)
                close()
                moveTo(19.1362f, 8.4712f)
                curveTo(19.1137f, 8.775f, 18.8549f, 9.0f, 18.5512f, 9.0f)
                horizontalLineTo(17.9887f)
                verticalLineTo(9.5513f)
                curveTo(17.9887f, 9.855f, 17.7637f, 10.1138f, 17.4599f, 10.1363f)
                curveTo(17.1337f, 10.1588f, 16.8637f, 9.9f, 16.8637f, 9.5737f)
                verticalLineTo(9.0f)
                horizontalLineTo(15.1649f)
                curveTo(15.0637f, 9.0f, 14.9737f, 8.9663f, 14.8837f, 8.9213f)
                curveTo(14.8724f, 8.91f, 14.8612f, 8.9213f, 14.8499f, 8.91f)
                curveTo(14.8387f, 8.8988f, 14.8274f, 8.8875f, 14.8162f, 8.8763f)
                curveTo(14.7824f, 8.8538f, 14.7599f, 8.8313f, 14.7374f, 8.7975f)
                curveTo(14.7149f, 8.7638f, 14.6924f, 8.7413f, 14.6812f, 8.7075f)
                curveTo(14.6699f, 8.6737f, 14.6474f, 8.6512f, 14.6474f, 8.6062f)
                curveTo(14.6362f, 8.5725f, 14.6249f, 8.5275f, 14.6249f, 8.4937f)
                curveTo(14.6249f, 8.4712f, 14.6137f, 8.46f, 14.6137f, 8.4375f)
                curveTo(14.6137f, 8.415f, 14.6249f, 8.4038f, 14.6249f, 8.3925f)
                curveTo(14.6249f, 8.3588f, 14.6362f, 8.3138f, 14.6474f, 8.28f)
                curveTo(14.6587f, 8.2463f, 14.6699f, 8.2125f, 14.6924f, 8.1788f)
                curveTo(14.7037f, 8.1675f, 14.7037f, 8.145f, 14.7149f, 8.1337f)
                lineTo(16.9762f, 4.7587f)
                curveTo(16.9874f, 4.7475f, 16.9987f, 4.7362f, 17.0099f, 4.725f)
                curveTo(17.0324f, 4.6912f, 17.0549f, 4.6687f, 17.0887f, 4.6462f)
                curveTo(17.1224f, 4.6238f, 17.1449f, 4.6013f, 17.1787f, 4.59f)
                curveTo(17.2124f, 4.5675f, 17.2462f, 4.5563f, 17.2799f, 4.545f)
                curveTo(17.3137f, 4.5338f, 17.3474f, 4.5225f, 17.3924f, 4.5225f)
                curveTo(17.4149f, 4.5225f, 17.4262f, 4.5113f, 17.4487f, 4.5113f)
                curveTo(17.4712f, 4.5113f, 17.4824f, 4.5225f, 17.5049f, 4.5225f)
                curveTo(17.5387f, 4.5225f, 17.5724f, 4.5338f, 17.6062f, 4.545f)
                curveTo(17.6399f, 4.5563f, 17.6737f, 4.5675f, 17.7074f, 4.59f)
                curveTo(17.7187f, 4.6013f, 17.7412f, 4.6013f, 17.7524f, 4.6125f)
                curveTo(17.7637f, 4.6238f, 17.7749f, 4.635f, 17.7862f, 4.6462f)
                curveTo(17.8199f, 4.6687f, 17.8424f, 4.6912f, 17.8649f, 4.725f)
                curveTo(17.8874f, 4.7587f, 17.9099f, 4.7813f, 17.9212f, 4.815f)
                curveTo(17.9324f, 4.8488f, 17.9549f, 4.8713f, 17.9549f, 4.9163f)
                curveTo(17.9662f, 4.95f, 17.9774f, 4.995f, 17.9774f, 5.0287f)
                curveTo(17.9774f, 5.0512f, 17.9887f, 5.0625f, 17.9887f, 5.085f)
                verticalLineTo(7.8975f)
                horizontalLineTo(18.5624f)
                curveTo(18.8999f, 7.875f, 19.1587f, 8.145f, 19.1362f, 8.4712f)
                close()
                moveTo(16.8749f, 7.875f)
                verticalLineTo(6.9187f)
                lineTo(16.2337f, 7.875f)
                horizontalLineTo(16.8749f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF433B6B)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(11.25f, 11.2612f)
                horizontalLineTo(12.375f)
                verticalLineTo(13.5112f)
                horizontalLineTo(11.25f)
                verticalLineTo(11.2612f)
                close()
                moveTo(23.625f, 11.2612f)
                horizontalLineTo(24.75f)
                verticalLineTo(13.5112f)
                horizontalLineTo(23.625f)
                verticalLineTo(11.2612f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF635994)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(15.75f, 31.5112f)
                horizontalLineTo(5.625f)
                verticalLineTo(20.925f)
                curveTo(5.625f, 20.5537f, 5.9175f, 20.2612f, 6.2888f, 20.2612f)
                horizontalLineTo(15.0863f)
                curveTo(15.4575f, 20.2612f, 15.75f, 20.5537f, 15.75f, 20.925f)
                verticalLineTo(31.5112f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF83CBFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(14.625f, 22.005f)
                verticalLineTo(29.7675f)
                curveTo(14.625f, 30.105f, 14.3437f, 30.3862f, 14.0062f, 30.3862f)
                horizontalLineTo(11.88f)
                curveTo(11.5425f, 30.3862f, 11.2612f, 30.105f, 11.2612f, 29.7675f)
                verticalLineTo(22.005f)
                curveTo(11.2612f, 21.6675f, 11.5425f, 21.3862f, 11.88f, 21.3862f)
                horizontalLineTo(14.0062f)
                curveTo(14.3437f, 21.3862f, 14.625f, 21.6675f, 14.625f, 22.005f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF83CBFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(10.125f, 22.005f)
                verticalLineTo(29.7675f)
                curveTo(10.125f, 30.105f, 9.8437f, 30.3862f, 9.5062f, 30.3862f)
                horizontalLineTo(7.38f)
                curveTo(7.0425f, 30.3862f, 6.7612f, 30.105f, 6.7612f, 29.7675f)
                verticalLineTo(22.005f)
                curveTo(6.7612f, 21.6675f, 7.0425f, 21.3862f, 7.38f, 21.3862f)
                horizontalLineTo(9.5062f)
                curveTo(9.8437f, 21.3862f, 10.125f, 21.6675f, 10.125f, 22.005f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF83CBFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(19.6762f, 27.0112f)
                horizontalLineTo(28.71f)
                curveTo(29.0025f, 27.0112f, 29.25f, 26.7637f, 29.2612f, 26.46f)
                verticalLineTo(23.0512f)
                curveTo(29.2612f, 22.7475f, 29.0137f, 22.5f, 28.71f, 22.5f)
                horizontalLineTo(19.6762f)
                curveTo(19.3725f, 22.5f, 19.125f, 22.7475f, 19.125f, 23.0512f)
                verticalLineTo(26.46f)
                curveTo(19.125f, 26.7637f, 19.3725f, 27.0112f, 19.6762f, 27.0112f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFF8312F)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero,
            ) {
                moveTo(32.895f, 18.0113f)
                horizontalLineTo(3.1163f)
                curveTo(2.6437f, 18.0113f, 2.25f, 17.6288f, 2.25f, 17.145f)
                verticalLineTo(14.3663f)
                curveTo(2.25f, 13.8938f, 2.6325f, 13.5f, 3.1163f, 13.5f)
                horizontalLineTo(32.895f)
                curveTo(33.3675f, 13.5f, 33.7612f, 13.8825f, 33.7612f, 14.3663f)
                verticalLineTo(17.145f)
                curveTo(33.75f, 17.6288f, 33.3675f, 18.0113f, 32.895f, 18.0113f)
                close()
            }
        }
            .build()
        return vector!!
    }
