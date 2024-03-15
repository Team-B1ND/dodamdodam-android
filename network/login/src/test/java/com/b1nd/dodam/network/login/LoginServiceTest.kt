package com.b1nd.dodam.network.login

import com.b1nd.dodam.common.exception.UnauthorizedException
import com.b1nd.dodam.network.login.api.LoginService
import com.b1nd.dodam.network.login.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginServiceTest {
    private lateinit var loginService: LoginService
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val client = HttpClient(
            MockEngine { _ ->
                respond(
                    content = """
                    {"status":401,"message":"비밀번호 틀림"}}
                    """.trimIndent(),
                    status = HttpStatusCode.Unauthorized,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            },
        ) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }
        }

        loginService = LoginService(client)
    }

    @Test
    fun testDeserializationOfLogin() = runTest(testDispatcher) {
        assertEquals(
            UnauthorizedException("비밀번호 틀림"),
            loginService.login("student", "1"),
        )
    }
}
