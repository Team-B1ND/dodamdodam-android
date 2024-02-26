package com.b1nd.dodam.network.core.util

import com.b1nd.dodam.network.core.model.Response
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

suspend inline fun <T> safeRequest(crossinline request: suspend () -> Response<T>): T {
    return when (request().status) {
        200, 201, 204 -> request().data ?: throw RuntimeException(request().message)
        else -> throw RuntimeException(request().message)
    }
}
