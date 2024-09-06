package com.b1nd.dodam.network.core.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.cio.CIO

internal actual fun getHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient =
    HttpClient(
        engine = CIO.create(),
        block = block
    )