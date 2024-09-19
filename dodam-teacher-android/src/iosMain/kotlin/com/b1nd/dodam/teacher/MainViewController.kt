package com.b1nd.dodam.teacher

import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSXPCConnection
import platform.UIKit.UIApplication

fun MainViewController(exit: () -> Unit) = ComposeUIViewController { DodamTeacherApp(exit = exit) }