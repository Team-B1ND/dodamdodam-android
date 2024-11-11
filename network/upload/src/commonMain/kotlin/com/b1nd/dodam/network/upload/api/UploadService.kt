package com.b1nd.dodam.network.upload.api

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.upload.datasource.UploadDataSource
import com.b1nd.dodam.network.upload.model.UploadResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

class UploadService(
    private val client: HttpClient
): UploadDataSource {
    override suspend fun upload(fileName: String, fileMimeType: String, byteArray: ByteArray): String {
        return safeRequest {
            client.post(DodamUrl.UPLOAD){
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                "file",
                                byteArray,
                                Headers.build {
                                    append(HttpHeaders.ContentType, fileMimeType)
                                    append(HttpHeaders.ContentDisposition, "filename=\"${fileName}\"")
                                },
                            )
                        },
                    ),
                )
                onUpload { bytesSentTotal, contentLength ->
                    println("Sent $bytesSentTotal bytes from $contentLength")
                }
            }.body<Response<String>>()
        }
    }
}