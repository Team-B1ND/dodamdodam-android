package com.b1nd.dodam.network.core.util

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

suspend inline fun <T> safeRequest(crossinline request: suspend () -> T): T {
    return try {
        request()
    } catch (e: RedirectResponseException) {
        // 3xx - response
        throw e
    } catch (e: ClientRequestException) {
        // 4xx - response
        throw e
    } catch (e: ServerResponseException) {
        // 5xx - response
        throw e
    }
}
