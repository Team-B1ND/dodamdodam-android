package kr.hs.dgsw.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.component.DodamTextField
import com.b1nd.dodam.designsystem.theme.BackIcon
import com.b1nd.dodam.designsystem.theme.DodamTheme

@Composable
internal fun LoginScreen(onBackClick: () -> Unit, onLoginClick: () -> Unit) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column {
        BackIcon(
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .clickable {
                    onBackClick()
                },
        )
        Column(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
        ) {
            Text(
                text = "아이디와 비밀번호를\n입력해주세요",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 32.dp),
            )
            DodamTextField(
                value = id,
                onValueChange = { id = it },
                onClickCancel = { id = "" },
                hint = "아이디",
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 32.dp),
            )
            DodamTextField(
                value = password,
                onValueChange = { password = it },
                onClickCancel = { password = "" },
                isPassword = true,
                isPasswordVisible = false,
                hint = "비밀번호",
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        }
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "비밀번호를 잊으셨나요?",
//                style = MaterialTheme.typography.labelMedium,
//                color = MaterialTheme.colorScheme.tertiary,)
//            Text(text = "비밀번호 재성정",
//                style = MaterialTheme.typography.labelMedium,
//                color = MaterialTheme.colorScheme.onBackground,)
//        }
        DodamFullWidthButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            onClick = { onLoginClick() },
            text = "로그인",
            enabled = id.isNotEmpty() && password.isNotEmpty(),
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun LoginScreenPreview() {
    DodamTheme {
        LoginScreen(
            {},
            {},
        )
    }
}
