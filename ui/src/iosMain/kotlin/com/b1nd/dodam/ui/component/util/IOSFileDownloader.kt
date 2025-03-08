package com.b1nd.dodam.ui.component.util

import com.b1nd.dodam.ui.util.FileDownloader
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURLSession
import platform.Foundation.NSUserDomainMask
import platform.Foundation.downloadTaskWithRequest

class IOSFileDownloader : FileDownloader {
    @OptIn(ExperimentalForeignApi::class)
    override fun downloadFile(fileName: String, fileUrl: String) {
        val url = NSURL(string = fileUrl)
        val request = NSURLRequest.requestWithURL(url)
        val session = NSURLSession.sharedSession
        val downloadTask = session.downloadTaskWithRequest(request) { location, _, error ->
            if (location != null) {
                val fileManager = NSFileManager.defaultManager
                val documentsDirectory = fileManager.URLsForDirectory(
                    NSDocumentDirectory,
                    NSUserDomainMask,
                ).first() as NSURL
                val destinationUrl = documentsDirectory.URLByAppendingPathComponent(fileName)
                if (destinationUrl != null) {
                    fileManager.moveItemAtURL(location, destinationUrl, null)
                }
            }
        }
        downloadTask.resume()
    }
}
