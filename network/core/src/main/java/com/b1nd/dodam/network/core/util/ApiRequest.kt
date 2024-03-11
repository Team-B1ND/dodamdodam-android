package com.b1nd.dodam.network.core.util

import com.b1nd.dodam.network.core.model.DefaultResponse
import com.b1nd.dodam.network.core.model.Response

suspend inline fun <T> safeRequest(crossinline request: suspend () -> Response<T>): T {
    val response = request()
    return when (response.status) {
        200, 201, 204 -> response.data
        else -> throw RuntimeException(response.message)
    }
}

suspend inline fun defaultSafeRequest(crossinline request: suspend () -> DefaultResponse) {
    val response = request()
    return when (response.status) {
        200, 201, 204 -> Unit
        else -> throw RuntimeException(response.message)
    }
}
