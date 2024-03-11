package com.b1nd.dodam.network.register

import com.b1nd.dodam.common.encryptSHA512
import com.b1nd.dodam.network.register.api.LoginService
import com.b1nd.dodam.network.register.model.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
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
                    {"status":200,"message":"로그인 성공","data":{"accessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6InF3ZXJsb3ZlMTAiLCJhY2Nlc3NMZXZlbCI6MSwiYXBpS2V5QWNjZXNzTGV2ZWwiOjAsImlhdCI6MTcxMDEyNTE1MCwiZXhwIjoxNzEwNTU3MTUwLCJpc3MiOiJkb2RhbS5jb20iLCJzdWIiOiJ0b2tlbiJ9.S4nGNcqHDUu_8qK0PP_AuTqojc8X1JJm-R8kS7hX0CM"}}
                    """.trimIndent(),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json"),
                )
            },
        ) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        loginService = LoginService(client)
    }

    @Test
    fun testDeserializationOfLogin() = runTest(testDispatcher) {
        assertEquals(
            LoginResponse(
                accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6InF3ZXJsb3ZlMTAiLCJhY2Nlc3NMZXZlbCI6MSwiYXBpS2V5QWNjZXNzTGV2ZWwiOjAsImlhdCI6MTcxMDEyNTE1MCwiZXhwIjoxNzEwNTU3MTUwLCJpc3MiOiJkb2RhbS5jb20iLCJzdWIiOiJ0b2tlbiJ9.S4nGNcqHDUu_8qK0PP_AuTqojc8X1JJm-R8kS7hX0CM"
            ),
            loginService.login("qwerlove10", "kim01387005!")
        )
    }
}
