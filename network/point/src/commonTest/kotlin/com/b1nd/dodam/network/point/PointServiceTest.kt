package com.b1nd.dodam.network.point

import com.b1nd.dodam.network.core.model.StudentResponse
import com.b1nd.dodam.network.core.model.TeacherResponse
import com.b1nd.dodam.network.point.api.PointService
import com.b1nd.dodam.network.point.model.NetworkPointType
import com.b1nd.dodam.network.point.model.NetworkScoreType
import com.b1nd.dodam.network.point.model.PointReasonResponse
import com.b1nd.dodam.network.point.model.PointResponse
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
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PointServiceTest {

    private lateinit var pointService: PointService
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
                            "message": "상벌점 조회 성공",
                            "data": [
                                {
                                    "id": 1,
                                    "student": {
                                        "id": 1,
                                        "name": "박박박",
                                        "grade": 1,
                                        "room": 1,
                                        "number": 1
                                    },
                                    "teacher": {
                                        "name": "교교교",
                                        "position": "교무부장",
                                        "tel": "0311234567"
                                    },
                                    "reason": {
                                        "id": 1,
                                        "reason": "청소 미흡",
                                        "score": 3,
                                        "scoreType": "MINUS",
                                        "pointType": "DORMITORY"
                                    },
                                    "issueAt": "2024-08-02"
                                }
                            ]
                            }
                        """.trimIndent(),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
            }
        }

        pointService = PointService(httpClient)
    }

    @Test
    fun 상벌점_조회() = runTest(testDispatcher) {
        val response = pointService.getMyPoint("기숙사")

        assertEquals(
            listOf(
                PointResponse(
                    id = 1,
                    student = StudentResponse(
                        id = 1,
                        grade = 1,
                        number = 1,
                        room = 1,
                        name = "박박박"
                    ),
                    teacher = TeacherResponse(
                        name = "교교교",
                        position = "교무부장",
                        tel = "0311234567"
                    ),
                    reason = PointReasonResponse(
                        id = 1,
                        reason = "청소 미흡",
                        score = 3,
                        scoreType = NetworkScoreType.MINUS,
                        pointType = NetworkPointType.DORMITORY
                    ),
                    issueAt = LocalDate.parse("2024-08-02")
                )
            ),
            response
        )
    }
}