package com.b1nd.dodam.club

import com.b1nd.dodam.club.api.BusService
import com.b1nd.dodam.club.model.BusResponse
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

class BusServiceTest {
    private lateinit var busService: BusService
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
                        DodamUrl.BUS == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status":200,
                                    "message":"버스 조회 성공",
                                    "data": [
                                            {
                                                "applyCount": 1,
                                                "busName": "동대구역 버스",
                                                "description": "동대구역",
                                                "id": 1,
                                                "leaveTime": "30분",
                                                "peopleLimit": 40,
                                                "timeRequired": "없음"
                                            }
                                        ]
                                    }
                                """.trimIndent(),
                            )
                        }
                        "${DodamUrl.Bus.APPLY}/1" == path && method == "POST" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "버스 추가 성공"
                                    }
                                """.trimIndent(),
                            )
                        }
                        "${DodamUrl.Bus.APPLY}/1" == path && method == "DELETE" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "버스 제거 성공"
                                    }
                                """.trimIndent(),
                            )
                        }

                        "${DodamUrl.Bus.APPLY}/1" == path && method == "PATCH" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "버스 수정 성공"
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.Bus.APPLY == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "버스 조회 성공",
                                    "data": {
                                        "applyCount": 1,
                                        "busName": "동대구역 버스",
                                        "description": "동대구역",
                                        "id": 1,
                                        "leaveTime": "30분",
                                        "peopleLimit": 40,
                                        "timeRequired": "없음"
                                    }
                                    }
                                """.trimIndent(),
                            )
                        }

                        else -> error("Unhandled $path")
                    }
                }
            }
        }
        busService = BusService(httpClient)
    }

    @Test
    fun 버스_전체_조회() = runTest(testDispatcher) {
        val response = busService.getBusList()

        assertEquals(
            BusResponse(
                applyCount = 1,
                busName = "동대구역 버스",
                description = "동대구역",
                id = 1,
                leaveTime = "30분",
                peopleLimit = 40,
                timeRequired = "없음",
            ),
            response.first(),
        )
    }

    @Test
    fun 버스_추가() = runTest(testDispatcher) {
        val response = busService.applyBus(1)

        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun 버스_제거() = runTest(testDispatcher) {
        val response = busService.deleteBus(1)

        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun 버스_변경() = runTest(testDispatcher) {
        val response = busService.updateBus(1)

        assertEquals(
            Unit,
            response,
        )
    }

    @Test
    fun 버스_본인_조회() = runTest(testDispatcher) {
        val response = busService.getMyBus()

        assertEquals(
            BusResponse(
                applyCount = 1,
                busName = "동대구역 버스",
                description = "동대구역",
                id = 1,
                leaveTime = "30분",
                peopleLimit = 40,
                timeRequired = "없음",
            ),
            response,
        )
    }
}
