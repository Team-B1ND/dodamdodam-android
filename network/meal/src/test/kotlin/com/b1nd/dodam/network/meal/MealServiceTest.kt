package com.b1nd.dodam.network.meal

import com.b1nd.dodam.network.meal.api.MealService
import com.b1nd.dodam.network.meal.model.MealResponse
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MealServiceTest {
    private lateinit var mealService: MealService
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val client = HttpClient(
            MockEngine { _ ->
                respond(
                    content = """
                    {
                        "status": 200,
                        "message": "급식 조회 성공",
                        "data": {
                            "exists": true,
                            "date": "2024-02-01",
                            "breakfast": "쇠고기우엉볶음밥 , 불고기치즈파니니 , 계란실파국 , 오이생채 , 배추김치 ",
                            "lunch": "*발아현미밥 , 맑은쇠고기무국, *매운갈비찜 , *핫스모크연어스테이크/소스 , 배추김치 , 눈사람도너츠 ",
                            "dinner": "*추가밥 , 돈코츠라멘 , -오징어야채볶음 , 배추김치 , 크럼블치킨/ "
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

        mealService = MealService(client, testDispatcher)
    }

    @Test
    fun testDeserializationOfMeal() = runTest(testDispatcher) {
        assertEquals(
            MealResponse(
                date = "2024-02-01",
                exists = true,
                breakfast = "쇠고기우엉볶음밥 , 불고기치즈파니니 , 계란실파국 , 오이생채 , 배추김치 ",
                lunch = "*발아현미밥 , 맑은쇠고기무국, *매운갈비찜 , *핫스모크연어스테이크/소스 , 배추김치 , 눈사람도너츠 ",
                dinner = "*추가밥 , 돈코츠라멘 , -오징어야채볶음 , 배추김치 , 크럼블치킨/ ",
            ),
            mealService.getMeal(2024, 2, 1).first().data,
        )
    }
}
