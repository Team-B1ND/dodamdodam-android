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
    Image(DodamLogo, null)
}

private var vector: ImageVector? = null

public val DodamLogo: ImageVector
    get() {
        if (vector != null) {
            return vector!!
        }
        vector = ImageVector.Builder(
            name = "Logo",
            defaultWidth = 88.dp,
            defaultHeight = 22.dp,
            viewportWidth = 88f,
            viewportHeight = 22f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF0083F0)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(0.0660301f, 18.0651f)
                curveTo(1.976f, 18.0651f, 5.1928f, 18.0651f, 8.5101f, 18.0651f)
                curveTo(8.5101f, 16.4571f, 8.5101f, 14.6481f, 8.6106f, 13.8441f)
                curveTo(9.3143f, 13.7436f, 11.4253f, 13.7436f, 12.33f, 13.8441f)
                curveTo(12.33f, 14.8491f, 12.33f, 16.5576f, 12.33f, 18.0651f)
                curveTo(16.2505f, 18.0651f, 19.7689f, 18.0651f, 20.8746f, 18.0651f)
                curveTo(20.9752f, 18.9696f, 20.9752f, 20.1756f, 20.8746f, 21.1806f)
                curveTo(18.7636f, 21.2811f, 2.9813f, 21.2811f, 0.1666f, 21.1806f)
                curveTo(-0.0345f, 20.2761f, -0.0345f, 18.9696f, 0.066f, 18.0651f)
                close()
                moveTo(1.37285f, 0.980038f)
                curveTo(3.6849f, 0.8795f, 16.8537f, 0.8795f, 19.2663f, 0.98f)
                curveTo(19.3668f, 1.8845f, 19.3668f, 3.392f, 19.2663f, 4.2965f)
                curveTo(17.5573f, 4.2965f, 6.9017f, 4.2965f, 5.0923f, 4.2965f)
                curveTo(5.0923f, 5.4021f, 5.0923f, 8.2161f, 5.0923f, 9.2211f)
                curveTo(7.0022f, 9.2211f, 17.9594f, 9.2211f, 19.6684f, 9.2211f)
                curveTo(19.7689f, 10.1256f, 19.7689f, 11.7336f, 19.6684f, 12.4371f)
                curveTo(17.6579f, 12.5376f, 3.6849f, 12.5376f, 1.2723f, 12.4371f)
                curveTo(1.1718f, 9.6231f, 1.2723f, 3.6936f, 1.3728f, 0.98f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF0083F0)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(22.6841f, 0.477377f)
                curveTo(24.9962f, 0.3769f, 32.3345f, 0.3769f, 34.7471f, 0.4774f)
                curveTo(34.8476f, 1.3819f, 34.8476f, 2.5879f, 34.7471f, 3.4924f)
                curveTo(33.0382f, 3.4924f, 28.1125f, 3.4924f, 26.303f, 3.4924f)
                curveTo(26.303f, 4.5979f, 26.303f, 6.3064f, 26.303f, 7.3114f)
                curveTo(28.213f, 7.3114f, 33.9429f, 7.3114f, 35.7524f, 7.3114f)
                curveTo(35.8529f, 8.2159f, 35.8529f, 9.6229f, 35.7524f, 10.3264f)
                curveTo(33.7419f, 10.4269f, 24.7952f, 10.4269f, 22.6841f, 10.3264f)
                curveTo(22.4831f, 7.6129f, 22.4831f, 3.2914f, 22.6841f, 0.4774f)
                close()
                moveTo(23.4883f, 12.5374f)
                curveTo(24.9962f, 12.4369f, 40.7786f, 12.4369f, 41.3817f, 12.5374f)
                curveTo(41.4823f, 14.4469f, 41.4823f, 20.0749f, 41.3817f, 21.884f)
                curveTo(39.7733f, 21.9845f, 25.2978f, 22.085f, 23.4883f, 21.884f)
                curveTo(23.3878f, 19.1704f, 23.3878f, 14.9494f, 23.4883f, 12.5374f)
                close()
                moveTo(37.8634f, 18.8689f)
                curveTo(37.8634f, 18.1654f, 37.8634f, 16.4569f, 37.8634f, 15.6529f)
                curveTo(36.5566f, 15.5524f, 28.6151f, 15.6529f, 27.0067f, 15.6529f)
                curveTo(27.0067f, 16.3564f, 26.9062f, 17.9644f, 27.0067f, 18.8689f)
                curveTo(28.1125f, 18.8689f, 36.8581f, 18.8689f, 37.8634f, 18.8689f)
                close()
                moveTo(37.8634f, 0.0753753f)
                curveTo(38.567f, -0.0251f, 40.5775f, -0.0251f, 41.4823f, 0.0754f)
                curveTo(41.4823f, 0.9799f, 41.4823f, 2.2864f, 41.4823f, 3.6934f)
                curveTo(42.387f, 3.6934f, 43.0907f, 3.6934f, 43.5933f, 3.6934f)
                curveTo(43.6938f, 4.5979f, 43.6938f, 6.0049f, 43.5933f, 6.9094f)
                curveTo(43.1912f, 6.9094f, 42.4875f, 7.0099f, 41.4823f, 7.0099f)
                curveTo(41.4823f, 8.8189f, 41.4823f, 10.4269f, 41.4823f, 11.3314f)
                curveTo(40.7786f, 11.4319f, 38.7681f, 11.4319f, 37.8634f, 11.3314f)
                curveTo(37.7629f, 9.4219f, 37.7629f, 1.8844f, 37.8634f, 0.0754f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF0083F0)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(44.3975f, 18.0651f)
                curveTo(46.3075f, 18.0651f, 49.5243f, 18.0651f, 52.9421f, 18.0651f)
                curveTo(52.9421f, 16.4571f, 52.9421f, 14.6481f, 52.9421f, 13.8441f)
                curveTo(53.6458f, 13.7436f, 55.7568f, 13.7436f, 56.6615f, 13.8441f)
                curveTo(56.6615f, 14.8491f, 56.6615f, 16.5576f, 56.6615f, 18.0651f)
                curveTo(60.582f, 18.0651f, 64.1004f, 18.0651f, 65.2061f, 18.0651f)
                curveTo(65.3067f, 18.9696f, 65.3067f, 20.1756f, 65.2061f, 21.1806f)
                curveTo(63.0951f, 21.2811f, 47.3127f, 21.2811f, 44.498f, 21.1806f)
                curveTo(44.297f, 20.2761f, 44.297f, 18.9696f, 44.3975f, 18.0651f)
                close()
                moveTo(45.7043f, 0.980038f)
                curveTo(48.0164f, 0.8795f, 61.1851f, 0.8795f, 63.5977f, 0.98f)
                curveTo(63.6983f, 1.8845f, 63.6983f, 3.392f, 63.5977f, 4.2965f)
                curveTo(61.8888f, 4.2965f, 51.2332f, 4.2965f, 49.4237f, 4.2965f)
                curveTo(49.4237f, 5.4021f, 49.4237f, 8.2161f, 49.4237f, 9.2211f)
                curveTo(51.3337f, 9.2211f, 62.2909f, 9.2211f, 63.9998f, 9.2211f)
                curveTo(64.1004f, 10.1256f, 64.1004f, 11.7336f, 63.9998f, 12.4371f)
                curveTo(61.9893f, 12.5376f, 48.0164f, 12.5376f, 45.6038f, 12.4371f)
                curveTo(45.6038f, 9.6231f, 45.6038f, 3.6936f, 45.7043f, 0.98f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF0083F0)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(67.0155f, 0.477377f)
                curveTo(69.3275f, 0.3769f, 76.6658f, 0.3769f, 79.0784f, 0.4774f)
                curveTo(79.179f, 1.3819f, 79.179f, 2.5879f, 79.0784f, 3.4924f)
                curveTo(77.3695f, 3.4924f, 72.4438f, 3.4924f, 70.6344f, 3.4924f)
                curveTo(70.6344f, 4.5979f, 70.6344f, 6.3064f, 70.6344f, 7.3114f)
                curveTo(72.5443f, 7.3114f, 78.2742f, 7.3114f, 80.0837f, 7.3114f)
                curveTo(80.1842f, 8.2159f, 80.1842f, 9.6229f, 80.0837f, 10.3264f)
                curveTo(78.0732f, 10.4269f, 69.227f, 10.4269f, 67.0155f, 10.3264f)
                curveTo(66.9149f, 7.6129f, 66.9149f, 3.2914f, 67.0155f, 0.4774f)
                close()
                moveTo(67.9202f, 12.5374f)
                curveTo(69.4281f, 12.4369f, 85.2104f, 12.4369f, 85.8136f, 12.5374f)
                curveTo(85.9141f, 14.4469f, 85.9141f, 20.0749f, 85.8136f, 21.884f)
                curveTo(84.2052f, 21.9845f, 69.7296f, 22.085f, 67.9202f, 21.884f)
                curveTo(67.7191f, 19.1704f, 67.8197f, 14.9494f, 67.9202f, 12.5374f)
                close()
                moveTo(82.2952f, 18.8689f)
                curveTo(82.3957f, 18.1654f, 82.3957f, 16.4569f, 82.2952f, 15.6529f)
                curveTo(80.9884f, 15.5524f, 73.0469f, 15.6529f, 71.4385f, 15.6529f)
                curveTo(71.338f, 16.3564f, 71.338f, 17.9644f, 71.4385f, 18.8689f)
                curveTo(72.5443f, 18.8689f, 81.29f, 18.8689f, 82.2952f, 18.8689f)
                close()
                moveTo(82.1947f, 0.0753753f)
                curveTo(82.8984f, -0.0251f, 84.9089f, -0.0251f, 85.8136f, 0.0754f)
                curveTo(85.8136f, 0.9799f, 85.8136f, 2.2864f, 85.8136f, 3.6934f)
                curveTo(86.7183f, 3.6934f, 87.422f, 3.6934f, 87.9246f, 3.6934f)
                curveTo(88.0251f, 4.5979f, 88.0251f, 6.0049f, 87.9246f, 6.9094f)
                curveTo(87.5225f, 6.9094f, 86.8188f, 7.0099f, 85.8136f, 7.0099f)
                curveTo(85.8136f, 8.8189f, 85.8136f, 10.4269f, 85.7131f, 11.3314f)
                curveTo(85.0094f, 11.4319f, 82.9989f, 11.4319f, 82.0942f, 11.3314f)
                curveTo(82.0942f, 9.4219f, 82.0942f, 1.8844f, 82.1947f, 0.0754f)
                close()
            }
        }.build()
        return vector!!
    }
