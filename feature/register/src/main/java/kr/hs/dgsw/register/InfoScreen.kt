package kr.hs.dgsw.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.theme.BackIcon

@Composable
fun InfoScreen(onBackClick: () -> Unit, onNextClick: () -> Unit) {
    Column {
        BackIcon(
            modifier = Modifier
                .padding(16.dp)
                .statusBarsPadding()
                .clickable { onBackClick() }
        )
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(text = "asd")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InfoScreenPreview() {
    InfoScreen({}, {})
}
