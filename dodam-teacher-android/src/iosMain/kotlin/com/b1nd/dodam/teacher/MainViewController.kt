package com.b1nd.dodam.teacher

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController(exit: () -> Unit) = ComposeUIViewController { DodamTeacherApp(exit = exit) }
