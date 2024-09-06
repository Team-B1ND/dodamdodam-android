package com.b1nd.dodam.network.core.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

internal actual fun getHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(
    engine = Darwin.create(),
    block = block,
)
