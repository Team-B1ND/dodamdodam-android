package com.b1nd.dodam.teacher

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.b1nd.dodam.ui.component.util.IOSFileDownloader
import com.b1nd.dodam.ui.util.LocalFileDownloader

fun MainViewController(exit: () -> Unit) = ComposeUIViewController {
    CompositionLocalProvider(LocalFileDownloader provides IOSFileDownloader()) {
        DodamTeacherApp(exit = exit)
    }
}
