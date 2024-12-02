package com.b1nd.dodam.ui.component

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.b1nd.dodam.designsystem.DodamTheme

@Composable
fun DodamAutoLinkText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    color: Color = Color.Black,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    linkColor: Color = DodamTheme.colors.primaryNormal,
    onLinkClick: (String) -> Unit = {},
) {
    val annotatedString = buildAnnotatedString {
        val urlRegex = Regex("(https?://[\\w\\-._~:/?#[\\\\]@!$&'()*+,;=%]+)")
        var lastIndex = 0

        // URL 찾기
        urlRegex.findAll(text).forEach { matchResult ->
            val start = matchResult.range.first
            val end = matchResult.range.last + 1
            val url = matchResult.value

            // 일반 텍스트 추가
            append(text.substring(lastIndex, start))

            // 링크 추가
            pushStringAnnotation(tag = "URL", annotation = url)
            withStyle(style = SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                append(url)
            }
            pop()

            lastIndex = end
        }

        // 남은 텍스트 추가
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }

    // 클릭 처리
    ClickableText(
        text = annotatedString,
        modifier = modifier,
        style = style.copy(
            color = color,
        ),
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    onLinkClick(annotation.item) // URL 클릭 이벤트 전달
                }
        },
    )
}
