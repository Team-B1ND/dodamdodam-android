package com.b1nd.dodam.network.register

import com.b1nd.dodam.register.api.RegisterService
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

class RegisterServiceTest {

    private lateinit var registerService: RegisterService
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        val httpClient = HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json()
            }

            engine {
                addHandler {
                    respond(
                        content = """
                            {
                            "status": 200,
                            "message": "회원가입 성공"
                            }
                        """.trimIndent(),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }
            }
        }

        registerService = RegisterService(httpClient)
    }

    @Test
    fun 회원가입_성공() = runTest(testDispatcher) {
        val response = registerService.register(
            email = "example@example.com",
            grade = 1,
            id = "example",
            name = "test",
            number = 1,
            phone = "01011112222",
            pw = "test",
            room = 1,
        )

        assertEquals(Unit, response)
    }
}
