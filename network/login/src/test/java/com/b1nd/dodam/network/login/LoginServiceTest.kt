package com.b1nd.dodam.network.login

import com.b1nd.dodam.common.encryptSHA512
import com.b1nd.dodam.network.login.api.LoginService
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
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
                            "memberResonse": {
                                "id": "1",
                                "name": "도현욱",
                                "email": "wsi1212@dgsw.hs.kr",
                                "role": "STUDENT",
                                "status": "ACTIVE",
                                "joinDate": "2024-01-26 15:29:17",
                                "profileImage": "https://dodam.kr.object.ncloudstorage.com/dodam/d248178f-9682-48d2-a09f-b9523b875956doala.png"
                            },
                            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6IjEiLCJhY2Nlc3NMZXZlbCI6MSwiYXBpS2V5QWNjZXNzTGV2ZWwiOjAsImlhdCI6MTcwNzc1OTEwNSwiZXhwIjoxNzA4MTkxMTA1LCJpc3MiOiJkb2RhbS5jb20iLCJzdWIiOiJ0b2tlbiJ9.kAcgi366DIGMceG4om6wZFJCbW2SoDaFZTkMU92FOLs",
                            "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJJZCI6IjEiLCJhY2Nlc3NMZXZlbCI6MSwiYXBpS2V5QWNjZXNzTGV2ZWwiOjAsImlhdCI6MTcwNzc1OTEwNSwiZXhwIjoxNzA4NjIzMTA1LCJpc3MiOiJkb2RhbS5jb20iLCJzdWIiOiJyZWZyZXNoVG9rZW4ifQ.KftbAj3HgZpVFyDbvVVvUOYDJl_UcA7UuJNBAzFkmOc"
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
        loginService.login("1", encryptSHA512("1")).data
    }
}
