package com.b1nd.dodam.network.register

import com.b1nd.dodam.common.encryptSHA512
import com.b1nd.dodam.network.register.api.LoginService
import com.b1nd.dodam.network.register.model.LoginResponse
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
                    {
                        "status": 200,
                        "message": "로그인 성공",
                        "data": {
                            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6IjEiLCJhY2Nlc3NMZXZlbCI6MSwiYXBpS2V5QWNjZXNzTGV2ZWwiOjAsImlhdCI6MTcwNzc1OTEwNSwiZXhwIjoxNzA4MTkxMTA1LCJpc3MiOiJkb2RhbS5jb20iLCJzdWIiOiJ0b2tlbiJ9.kAcgi366DIGMceG4om6wZFJCbW2SoDaFZTkMU92FOLs"
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
                accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6IjEiLCJhY2Nlc3NMZXZlbCI6MSwiYXBpS2V5QWNjZXNzTGV2ZWwiOjAsImlhdCI6MTcwNzc1OTEwNSwiZXhwIjoxNzA4MTkxMTA1LCJpc3MiOiJkb2RhbS5jb20iLCJzdWIiOiJ0b2tlbiJ9.kAcgi366DIGMceG4om6wZFJCbW2SoDaFZTkMU92FOLs"
            ),
            loginService.login("1", encryptSHA512("1"))
        )
    }
}
