package com.b1nd.dodam.network.nightstudy

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.NetworkStatus
import com.b1nd.dodam.network.core.model.StudentResponse
import com.b1nd.dodam.network.nightstudy.api.NightStudyService
import com.b1nd.dodam.network.nightstudy.model.NightStudyResponse
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
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NightStudyServiceTest {

    private lateinit var nightStudyService: NightStudyService
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        val httpClient = HttpClient(MockEngine) {

            engine {
                addHandler { request ->
                    val path = "https://" + request.url.host + request.url.fullPath
                    val method = request.method.value

                    fun MockRequestHandleScope.makeOkRespond(
                        content: String
                    ): HttpResponseData =
                        respond(
                            content = content,
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    when {
                        DodamUrl.NightStudy.MY == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "심자 신청 조회 성공",
                                    "data": [
                                        {
                                          "id": 1,
                                          "content": "example",
                                          "status": "ALLOWED",
                                          "doNeedPhone": true,
                                          "reasonForPhone": "test",
                                          "student": {
                                            "grade": 1,
                                            "id": 1,
                                            "name": "박박박",
                                            "number": 1,
                                            "room": 1
                                          },
                                          "place": "프로그래밍실1",
                                          "startAt": "2023-08-01",
                                          "endAt": "2023-08-31",
                                          "createdAt": "2023-07-01T12:00:00",
                                          "modifiedAt": "2023-07-15T15:30:00",
                                          "rejectReason": null
                                        }
                                    ]
                                    }
                                """.trimIndent()
                            )
                        }
                        DodamUrl.NIGHT_STUDY == path -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "심자 신청 성공"
                                    }
                                """.trimIndent()
                            )
                            respond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "심자 신청 성공"
                                    }
                                """.trimIndent(),
                                status = HttpStatusCode.OK,
                                headers = headersOf(HttpHeaders.ContentType, "application/json")
                            )
                        }
                        "${DodamUrl.NIGHT_STUDY}/1" == path && method == "DELETE" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "심자 신청 취소 성공"
                                    }
                                """.trimIndent()
                            )
                        }
                        else -> error("not found $path")
                    }
                }
            }
            install(ContentNegotiation) {
                json()
            }
        }

        nightStudyService = NightStudyService(httpClient)
    }

    @Test
    fun 심자_신청_조회() = runTest(testDispatcher) {
        val response = nightStudyService.getMyNightStudy()

        assertEquals(
            listOf(
                NightStudyResponse(
                    id = 1,
                    content = "example",
                    status = NetworkStatus.ALLOWED,
                    doNeedPhone = true,
                    "test",
                    student = StudentResponse(
                        id = 1,
                        grade = 1,
                        number = 1,
                        room = 1,
                        name = "박박박",
                    ),
                    place = "프로그래밍실1",
                    startAt = LocalDate.parse("2023-08-01"),
                    endAt = LocalDate.parse("2023-08-31"),
                    createdAt = LocalDateTime.parse("2023-07-01T12:00:00"),
                    modifiedAt = LocalDateTime.parse("2023-07-15T15:30:00"),
                    rejectReason = null
                )
            ),
            response
        )
    }

    @Test
    fun 심자_신청_추가() = runTest(testDispatcher) {
        val response = nightStudyService.askNightStudy(
            place = "프로그래밍실1",
            content = "example",
            doNeedPhone = true,
            reasonForPhone = "test",
            startAt = LocalDate.parse("2023-08-01"),
            endAt = LocalDate.parse("2023-08-01"),
        )

        assertEquals(
            Unit,
            response
        )
    }

    @Test
    fun 심자_신청_취소() = runTest(testDispatcher) {
        val response = nightStudyService.deleteNightStudy(
            id = 1
        )

        assertEquals(
            Unit,
            response
        )
    }
}