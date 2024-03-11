package com.b1nd.dodam.network.register

import com.b1nd.dodam.network.register.api.LoginService
import com.b1nd.dodam.network.register.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
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
                    {"status":200,"message":"로그인 성공","data":{"accessToken":"token"}}
                    """.trimIndent(),
                    status = HttpStatusCode.OK,
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
            LoginResponse(
                accessToken = "token",
            ),
            loginService.login("qwerlove10", "kim01387005!"),
        )
    }
}
