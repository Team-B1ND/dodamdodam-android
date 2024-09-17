package com.b1nd.dodam.network.member

import com.b1nd.dodam.member.api.MemberService
import com.b1nd.dodam.member.model.MemberInfoResponse
import com.b1nd.dodam.member.model.StudentResponse
import com.b1nd.dodam.member.model.TeacherResponse
import com.b1nd.dodam.network.core.DodamUrl
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

class MemberServiceTest {

    private lateinit var memberService: MemberService
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        val httpClient = HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json()
            }
            engine {
                addHandler { request ->
                    val path = "https://" + request.url.host + request.url.fullPath

                    fun MockRequestHandleScope.makeOkRespond(content: String): HttpResponseData = respond(
                        content = content,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )

                    when (path) {
                        DodamUrl.Member.MY -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "멤버 정보 불러오기 성공",
                                    "data": {

                                      "createdAt": "2023-08-01T12:00:00Z",
                                      "email": "example@example.com",
                                      "id": "12345",
                                      "modifiedAt": "2023-08-02T15:30:00Z",
                                      "name": "박박박",
                                      "phone": "01012345678",
                                      "profileImage": "https://example.com/profile.jpg",
                                      "role": "student",
                                      "status": "active",
                                      "student": {
                                        "grade": 10,
                                        "id": 1001,
                                        "name": "박박박",
                                        "number": 5,
                                        "room": 12
                                      },
                                      "teacher": {
                                        "id": 2001,
                                        "position": "교무부장",
                                        "tel": "0311234567"
                                      }
                                    }
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.Member.DEACTIVATION -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "회원 탈퇴 성공"
                                    }
                                """.trimIndent(),
                            )
                        }
                        else -> error("not valid $path")
                    }
                }
            }
        }

        memberService = MemberService(httpClient)
    }

    @Test
    fun 자신_정보_조회() = runTest(testDispatcher) {
        val response = memberService.getMyInfo()

        assertEquals(
            MemberInfoResponse(
                createdAt = "2023-08-01T12:00:00Z",
                email = "example@example.com",
                id = "12345",
                modifiedAt = "2023-08-02T15:30:00Z",
                name = "박박박",
                phone = "01012345678",
                profileImage = "https://example.com/profile.jpg",
                role = "student",
                status = "active",
                student = StudentResponse(
                    grade = 10,
                    id = 1001,
                    name = "박박박",
                    number = 5,
                    room = 12,
                ),
                teacher = TeacherResponse(
                    id = 2001,
                    position = "교무부장",
                    tel = "0311234567",
                ),
            ),
            response,
        )
    }

    @Test
    fun 멤버_회원탈퇴() = runTest(testDispatcher) {
        val response = memberService.deactivation()

        assertEquals(
            Unit,
            response,
        )
    }
}
