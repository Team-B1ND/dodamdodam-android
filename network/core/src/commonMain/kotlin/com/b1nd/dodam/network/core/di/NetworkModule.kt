package com.b1nd.dodam.network.core.di

import com.b1nd.dodam.common.exception.UnauthorizedException
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import com.b1nd.dodam.logging.logging
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.model.TokenRequest
import com.b1nd.dodam.network.core.model.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val TIME_OUT = 60_000L

val log = logging()

internal expect fun getHttpClient(block: HttpClientConfig<*>.() -> Unit = {}): HttpClient
internal expect fun getUserAgent(): String

val networkCoreModule = module {
    single<HttpClient> {
        val datastore: DataStoreRepository = get()

        getHttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }

            install(UserAgent) {
                agent = getUserAgent()
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        log.i("HttpClient") { message }
                    }
                }
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        try {
                            val accessToken = datastore.user.first().token
                            BearerTokens(accessToken, "")
                        } catch (e: IndexOutOfBoundsException) {
                            throw UnauthorizedException("${e.message}")
                        }
                    }
                    refreshTokens {
                        val user = datastore.user.first()
                        val accessToken = client.post(DodamUrl.Auth.LOGIN) {
                            markAsRefreshTokenRequest()
                            setBody(TokenRequest(id = user.id, pw = user.pw))
                        }.body<Response<TokenResponse>>().data?.accessToken ?: ""

                        datastore.saveToken(accessToken)

                        BearerTokens(accessToken, "")
                    }
                    sendWithoutRequest { request ->
                        when (request.url.toString()) {
                            DodamUrl.Auth.LOGIN -> false
                            DodamUrl.Member.REGISTER -> false
                            DodamUrl.GET_BUNDLE_ID -> false
                            else -> true
                        }
                    }
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIME_OUT
                connectTimeoutMillis = TIME_OUT
                socketTimeoutMillis = TIME_OUT
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    }
}
