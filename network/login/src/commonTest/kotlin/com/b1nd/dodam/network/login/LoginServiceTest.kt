package com.b1nd.dodam.network.login

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
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

class LoginServiceTest {
    private lateinit var loginService: LoginService
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        val client = HttpClient(
            MockEngine {
                respond(
                    content = """
                        {
                        "status":200,
                        "message":"로그인 성공",
                        "data":{
                            "accessToken":"token"
                            }
                        }
                    """.trimIndent(),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            },
        ) {
            install(ContentNegotiation) {
                json()
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
            loginService.login("student", "12345"),
        )
    }
}
