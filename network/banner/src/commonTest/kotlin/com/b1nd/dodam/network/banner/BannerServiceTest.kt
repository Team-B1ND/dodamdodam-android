package com.b1nd.dodam.network.banner

import com.b1nd.dodam.network.banner.api.BannerService
import com.b1nd.dodam.network.banner.model.BannerResponse
import com.b1nd.dodam.network.banner.model.NetworkBannerStatus
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
import kotlinx.datetime.LocalDateTime

class BannerServiceTest {

    private lateinit var bannerService: BannerService
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        val httpClient = HttpClient(
            MockEngine {
                respond(
                    content = """
                        {
                        "status":200,
                        "message":"로그인 성공",
                        "data": [{
                            "id": 1,
                            "imageUrl": "https://example.com",
                            "redirectUrl": "https://example.com",
                            "title": "title",
                            "status": "ACTIVE",
                            "expireAt": "2024-05-31T23:59:59"
                            }
                        ]}
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

        bannerService = BannerService(httpClient)
    }

    @Test
    fun 유효_배너_불러오기() = runTest(testDispatcher) {
        val response = bannerService.getActiveBanner()
        assertEquals(
            BannerResponse(
                id = 1,
                imageUrl = "https://example.com",
                redirectUrl = "https://example.com",
                title = "title",
                status = NetworkBannerStatus.ACTIVE,
                expireAt = LocalDateTime.parse("2024-05-31T23:59:59"),
            ),
            response.first(),
        )
    }
}
