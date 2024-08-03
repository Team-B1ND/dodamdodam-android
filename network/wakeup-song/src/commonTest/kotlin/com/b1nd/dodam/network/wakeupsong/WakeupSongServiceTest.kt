package com.b1nd.dodam.network.wakeupsong

import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.NetworkStatus
import com.b1nd.dodam.wakeupsong.api.WakeupSongService
import com.b1nd.dodam.wakeupsong.model.MelonChartSongResponse
import com.b1nd.dodam.wakeupsong.model.SearchWakeupSongResponse
import com.b1nd.dodam.wakeupsong.model.WakeupSongResponse
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
import kotlinx.serialization.json.JsonNull.content

class WakeupSongServiceTest {
    private lateinit var wakeupSongService: WakeupSongService
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
                        "${DodamUrl.WakeupSong.ALLOWED}?year=2024&month=7&day=29" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "오늘의 기상곡 불러오기 성공",
                                    "data": [
                                        {
                                            "id": 1,
                                            "thumbnail": "https://example.com/test.jpg",
                                            "videoTitle": "test",
                                            "videoId": "q3R5W6",
                                            "videoUrl": "https://youtube.com/watch?v=q3R5W6",
                                            "channelTitle": "test",
                                            "status": "ALLOWED",
                                            "createdAt": "2024-07-29"
                                        }
                                    ]
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.WakeupSong.MY == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "신청한 기상곡 불러오기 성공",
                                    "data": [
                                        {
                                            "id": 1,
                                            "thumbnail": "https://example.com/test.jpg",
                                            "videoTitle": "test",
                                            "videoId": "q3R5W6",
                                            "videoUrl": "https://youtube.com/watch?v=q3R5W6",
                                            "channelTitle": "test",
                                            "status": "PENDING",
                                            "createdAt": "2024-07-29"
                                        }
                                    ]
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.WakeupSong.PENDING == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "대기중인 기상곡 불러오기 성공",
                                    "data": [
                                        {
                                            "id": 1,
                                            "thumbnail": "https://example.com/test.jpg",
                                            "videoTitle": "test",
                                            "videoId": "q3R5W6",
                                            "videoUrl": "https://youtube.com/watch?v=q3R5W6",
                                            "channelTitle": "test",
                                            "status": "PENDING",
                                            "createdAt": "2024-07-29"
                                        }
                                    ]
                                    }
                                """.trimIndent(),
                            )
                        }
                        "${DodamUrl.WakeupSong.MY}/1" == path && method == "DELETE" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "대기중인 기상곡 불러오기 성공"
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.WakeupSong.KEY_WORD == path && method == "POST" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "기상송 신청 성공"
                                    }
                                """.trimIndent(),
                            )
                        }
                        "${DodamUrl.WakeupSong.SEARCH}?keyword=test" == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "기상송 검색 성공",
                                    "data": [
                                        {
                                            "thumbnail": "https://example.com/test.jpg",
                                            "videoTitle": "test",
                                            "videoId": "q3R5W6",
                                            "videoUrl": "https://youtube.com/watch?v=q3R5W6",
                                            "channelTitle": "test"
                                        }
                                    ]
                                    }
                                """.trimIndent(),
                            )
                        }
                        DodamUrl.WakeupSong.CHART == path && method == "GET" -> {
                            makeOkRespond(
                                content = """
                                    {
                                    "status": 200,
                                    "message": "멜론 차트 불러오기 성공",
                                    "data": [
                                        {
                                            "album": "test",
                                            "artist": "test",
                                            "name": "test",
                                            "rank": 1,
                                            "thumbnail": "https://example.com/test.jpg"
                                        }
                                    ]
                                    }
                                """.trimIndent(),
                            )
                        }
                        else -> error("not found $path")
                    }
                }
            }
        }
        wakeupSongService = WakeupSongService(httpClient)
    }

    @Test
    fun 오늘의_기상곡_불러오기() = runTest(testDispatcher) {
        val response = wakeupSongService.getAllowedWakeupSongs(2024, 7, 29)
        assertEquals(
            listOf(
                WakeupSongResponse(
                    id = 1,
                    thumbnail = "https://example.com/test.jpg",
                    videoTitle = "test",
                    videoId = "q3R5W6",
                    videoUrl = "https://youtube.com/watch?v=q3R5W6",
                    channelTitle = "test",
                    status = NetworkStatus.ALLOWED,
                    createdAt = LocalDate(2024, 7, 29),
                ),
            ),
            response,
        )
    }

    @Test
    fun 신청한_기상곡_불러오기() = runTest(testDispatcher) {
        val response = wakeupSongService.getMyWakeupSongs()
        assertEquals(
            listOf(
                WakeupSongResponse(
                    id = 1,
                    thumbnail = "https://example.com/test.jpg",
                    videoTitle = "test",
                    videoId = "q3R5W6",
                    videoUrl = "https://youtube.com/watch?v=q3R5W6",
                    channelTitle = "test",
                    status = NetworkStatus.PENDING,
                    createdAt = LocalDate(2024, 7, 29),
                ),
            ),
            response,
        )
    }

    @Test
    fun 대기중_기상송_불러오기() = runTest(testDispatcher) {
        val response = wakeupSongService.getPendingWakeupSongs()

        assertEquals(
            listOf(
                WakeupSongResponse(
                    id = 1,
                    thumbnail = "https://example.com/test.jpg",
                    videoTitle = "test",
                    videoId = "q3R5W6",
                    videoUrl = "https://youtube.com/watch?v=q3R5W6",
                    channelTitle = "test",
                    status = NetworkStatus.PENDING,
                    createdAt = LocalDate(2024, 7, 29),
                ),
            ),
            response,
        )
    }

    @Test
    fun 기상송_삭제하기() = runTest(testDispatcher) {
        val response = wakeupSongService.deleteWakeupSong(1)

        assertEquals(Unit, response)
    }

    @Test
    fun 기상송_검색하기() = runTest(testDispatcher) {
        val response = wakeupSongService.searchWakeupSong("test")

        assertEquals(
            listOf(
                SearchWakeupSongResponse(
                    thumbnail = "https://example.com/test.jpg",
                    videoTitle = "test",
                    videoId = "q3R5W6",
                    videoUrl = "https://youtube.com/watch?v=q3R5W6",
                    channelTitle = "test",
                ),
            ),
            response,
        )
    }

    @Test
    fun 기상송_멜론차트_불러오기() = runTest(testDispatcher) {
        val response = wakeupSongService.getMelonChart()

        assertEquals(
            listOf(
                MelonChartSongResponse(
                    thumbnail = "https://example.com/test.jpg",
                    artist = "test",
                    name = "test",
                    rank = 1,
                    album = "test",
                ),
            ),
            response,
        )
    }
}
