package com.b1nd.dodam.teacher

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.b1nd.dodam.keystore.KeyStoreManager

@Composable
fun DodamTeacherApp() {
    val text = "DODAM LOVE"
    val keyStoreManager = KeyStoreManager()
    MaterialTheme {
        Column {
            Text(keyStoreManager.encrypt(text))
            Text(keyStoreManager.decrypt(keyStoreManager.encrypt(text)))
        }
    }
}
