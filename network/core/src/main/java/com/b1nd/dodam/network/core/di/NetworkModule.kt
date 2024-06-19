package com.b1nd.dodam.network.core.di

import android.util.Log
import com.b1nd.dodam.common.exception.UnauthorizedException
import com.b1nd.dodam.datastore.repository.DatastoreRepository
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.model.TokenRequest
import com.b1nd.dodam.network.core.model.TokenResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
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
import javax.inject.Singleton
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import org.koin.dsl.module

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient(datastore: DatastoreRepository): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("HttpClient", message)
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

private const val TIME_OUT = 60_000L

val networkCoreModule = module {
    single<HttpClient> {
        val datastore: DatastoreRepository = get()
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i("HttpClient", message)
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
