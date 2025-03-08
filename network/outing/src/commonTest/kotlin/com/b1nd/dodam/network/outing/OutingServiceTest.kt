package com.b1nd.dodam.network.outing

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.NetworkStatus
import com.b1nd.dodam.network.core.model.StudentResponse
import com.b1nd.dodam.network.outing.api.OutingService
import com.b1nd.dodam.network.outing.model.OutingResponse
import com.b1nd.dodam.network.outing.model.SleepoverResponse
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonNull.content

class OutingServiceTest {

    private lateinit var outingService: OutingService
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
                    val method = request.method.value

                    fun MockRequestHandleScope.makeOkRespond(content: String): HttpResponseData = respond(
                        content = content,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )

                    when {
                        DodamUrl.Outing.MY == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "외출 조회 성공",
                                    "data": [
                                        {
                                            "id": 1,
                                            "reason": "test",
                                            "status": "ALLOWED",
                                            "student": {
                                                "id": 1,
                                                "name": "박박박",
                                                "grade": 1,
                                                "room": 1,
                                                "number": 1
                                            },
                                            "startAt": "2024-08-01T09:00:00",
                                            "endAt": "2024-08-01T11:00:00",
                                            "createdAt": "2024-07-31T14:30:00",
                                            "modifiedAt": "2024-08-01T08:00:00",
                                            "rejectReason": null
                                        }
                                    ]
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.Sleepover.MY == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "외박 조회 성공",
                                    "data": [
                                        {
                                            "id": 1,
                                            "reason": "test",
                                            "status": "ALLOWED",
                                            "student": {
                                                "id": 1,
                                                "name": "박박박",
                                                "grade": 1,
                                                "room": 1,
                                                "number": 1
                                            },
                                            "startAt": "2024-08-01",
                                            "endAt": "2024-08-01",
                                            "createdAt": "2024-07-31T14:30:00",
                                            "modifiedAt": "2024-08-01T08:00:00",
                                            "rejectReason": null
                                        }
                                    ]
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.OUTING == path && method == "POST" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "외출 등록 성공"
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.SLEEPOVER == path && method == "POST" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "외박 등록 성공"
                                    }
                                """.trimIndent(),
                            )
                        }
                        "${DodamUrl.OUTING}/1" == path && method == "DELETE" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "외출 삭제 성공"
                                    }
                                """.trimIndent(),
                            )
                        }

                        "${DodamUrl.SLEEPOVER}/1" == path && method == "DELETE" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "외박 삭제 성공"
                                    }
                                """.trimIndent(),
                            )
                        }
                        else -> error("not found $path")
                    }
                }
            }
        }

        outingService = OutingService(httpClient)
    }

    @Test
    fun getOuting() = runTest(testDispatcher) {
        val response = outingService.getMyOuting()

        assertEquals(
            listOf(
                OutingResponse(
                    id = 1,
                    reason = "test",
                    status = NetworkStatus.ALLOWED,
                    student = StudentResponse(
                        id = 1,
                        name = "박박박",
                        grade = 1,
                        room = 1,
                        number = 1,
                    ),
                    startAt = LocalDateTime.parse("2024-08-01T09:00:00"),
                    endAt = LocalDateTime.parse("2024-08-01T11:00:00"),
                    createdAt = LocalDateTime.parse("2024-07-31T14:30:00"),
                    modifiedAt = LocalDateTime.parse("2024-08-01T08:00:00"),
                    rejectReason = null,
                ),
            ),
            response,
        )
    }

    @Test
    fun getOutSleep() = runTest(testDispatcher) {
        val response = outingService.getMySleepover()

        assertEquals(
            listOf(
                SleepoverResponse(
                    id = 1,
                    reason = "test",
                    status = NetworkStatus.ALLOWED,
                    student = StudentResponse(
                        id = 1,
                        name = "박박박",
                        grade = 1,
                        room = 1,
                        number = 1,
                    ),
                    startAt = LocalDate.parse("2024-08-01"),
                    endAt = LocalDate.parse("2024-08-01"),
                    createdAt = LocalDateTime.parse("2024-07-31T14:30:00"),
                    modifiedAt = LocalDateTime.parse("2024-08-01T08:00:00"),
                    rejectReason = null,
                ),
            ),
            response,
        )
    }

    @Test
    fun applyOuting() = runTest(testDispatcher) {
        val response = outingService.askOuting(
            reason = "test",
            startAt = LocalDateTime.parse("2024-07-31T14:30:00"),
            endAt = LocalDateTime.parse("2024-07-31T17:30:00"),
            isDinner = true,
        )

        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun applyOutSleep() = runTest(testDispatcher) {
        val response = outingService.askSleepover(
            reason = "test",
            startAt = LocalDate.parse("2024-07-31"),
            endAt = LocalDate.parse("2024-08-01"),
        )

        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun delOuting() = runTest(testDispatcher) {
        val response = outingService.deleteOuting(1)

        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun dleOutSleep() = runTest(testDispatcher) {
        val response = outingService.deleteSleepover(1)

        assertEquals(
            Unit,
            response,
        )
    }
}
