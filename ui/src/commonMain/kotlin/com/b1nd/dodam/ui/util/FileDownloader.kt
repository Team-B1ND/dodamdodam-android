package com.b1nd.dodam.ui.util

import androidx.compose.runtime.staticCompositionLocalOf

interface FileDownloader {
    fun downloadFile(fileName: String, fileUrl: String)
}

val LocalFileDownloader = staticCompositionLocalOf<FileDownloader> {
    error("No FileDownloader provided")
}
