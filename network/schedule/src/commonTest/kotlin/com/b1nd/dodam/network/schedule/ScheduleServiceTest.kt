package com.b1nd.dodam.network.schedule

import com.b1nd.dodam.network.schedule.api.ScheduleService
import com.b1nd.dodam.network.schedule.model.NetworkGrade
import com.b1nd.dodam.network.schedule.model.NetworkScheduleType
import com.b1nd.dodam.network.schedule.model.ScheduleResponse
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
import kotlinx.datetime.LocalDate

class ScheduleServiceTest {

    private lateinit var scheduleService: ScheduleService
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
                            "message": "일정 불러오기 성공",
                            "data": [
                                {
                                    "id": 1,
                                    "name": "해커톤",
                                    "place": "강당",
                                    "type": "ACADEMIC",
                                    "date": ["2024-08-03", "2024-08-04"],
                                    "targetGrades": ["GRADE_1", "GRADE_2"]
                                }
                            ]
                            }
                        """.trimIndent(),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                }
            }
        }

        scheduleService = ScheduleService(httpClient)
    }

    @Test
    fun 일정_조회() = runTest(testDispatcher) {
        val response = scheduleService.getScheduleBetweenPeriods(
            startDate = "2024-08-03",
            endDate = "2024-08-04",
        )

        assertEquals(
            listOf(
                ScheduleResponse(
                    id = 1,
                    name = "해커톤",
                    place = "강당",
                    type = NetworkScheduleType.ACADEMIC,
                    date = listOf(LocalDate(2024, 8, 3), LocalDate(2024, 8, 4)),
                    targetGrades = listOf(NetworkGrade.GRADE_1, NetworkGrade.GRADE_2),
                ),
            ),
            response,
        )
    }
}
