package com.b1nd.dodam.ui.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log

class AndroidFileDownloader(private val context: Context) : FileDownloader {

    override fun downloadFile(fileName: String, fileUrl: String) {
        try {
            val request = DownloadManager.Request(Uri.parse(fileUrl)).apply {
                setTitle(fileName)
                setDescription("Downloading $fileName")
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            }
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
        } catch (e: Exception) {
            Log.d("TAG", "downloadFile: $e")
        }
    }
}
