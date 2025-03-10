package com.b1nd.dodam.network.core.util

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider

fun HttpClient.clearToken() {
    this.authProviders
        .filterIsInstance<BearerAuthProvider>()
        .forEach {
            it.clearToken()
        }
}