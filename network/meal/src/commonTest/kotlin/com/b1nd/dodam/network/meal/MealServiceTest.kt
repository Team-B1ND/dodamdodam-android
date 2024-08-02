package com.b1nd.dodam.network.meal

import com.b1nd.dodam.network.meal.api.MealService
import com.b1nd.dodam.network.meal.model.MealDetailResponse
import com.b1nd.dodam.network.meal.model.MealResponse
import com.b1nd.dodam.network.meal.model.MenuResponse
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

class MealServiceTest {
    private lateinit var mealService: MealService
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        val client = HttpClient(
            MockEngine {
                respond(
                    content = """
                        {
                            "status": 200,
                            "message": "급식 조회 성공",
                            "data": {
                                "exists": true,
                                "date": "2024-02-01",
                                "breakfast": {
                                    "details": [
                                        {
                                            "name": "쇠고기우엉볶음밥",
                                            "allergies": [
                                                5,
                                                6,
                                                16
                                            ]
                                        },
                                        {
                                            "name": "불고기치즈파니니",
                                            "allergies": [
                                                1,
                                                2,
                                                5,
                                                6,
                                                10,
                                                13
                                            ]
                                        },
                                        {
                                            "name": "계란실파국",
                                            "allergies": [
                                                1,
                                                5,
                                                6
                                            ]
                                        },
                                        {
                                            "name": "오이생채",
                                            "allergies": [
                                                13
                                            ]
                                        },
                                        {
                                            "name": "배추김치",
                                            "allergies": [
                                                9
                                            ]
                                        }
                                    ],
                                    "calorie": 709.9
                                },
                                "lunch": {
                                    "details": [
                                        {
                                            "name": "발아현미밥",
                                            "allergies": []
                                        },
                                        {
                                            "name": "맑은쇠고기무국1",
                                            "allergies": [
                                                5,
                                                6,
                                                16
                                            ]
                                        },
                                        {
                                            "name": "매운갈비찜",
                                            "allergies": [
                                                5,
                                                6,
                                                10,
                                                13
                                            ]
                                        },
                                        {
                                            "name": "핫스모크연어스테이크/소스",
                                            "allergies": [
                                                1,
                                                2,
                                                5,
                                                6
                                            ]
                                        },
                                        {
                                            "name": "배추김치",
                                            "allergies": [
                                                9
                                            ]
                                        },
                                        {
                                            "name": "눈사람도너츠",
                                            "allergies": [
                                                1,
                                                2,
                                                5,
                                                6
                                            ]
                                        }
                                    ],
                                    "calorie": 1084.6
                                },
                                "dinner": {
                                    "details": [
                                        {
                                            "name": "추가밥",
                                            "allergies": []
                                        },
                                        {
                                            "name": "돈코츠라멘",
                                            "allergies": [
                                                1,
                                                2,
                                                5,
                                                6,
                                                10,
                                                13,
                                                15,
                                                16
                                            ]
                                        },
                                        {
                                            "name": "오징어야채볶음",
                                            "allergies": [
                                                5,
                                                6,
                                                13,
                                                17
                                            ]
                                        },
                                        {
                                            "name": "배추김치",
                                            "allergies": [
                                                9
                                            ]
                                        },
                                        {
                                            "name": "크럼블치킨/",
                                            "allergies": [
                                                1,
                                                2,
                                                5,
                                                6,
                                                15,
                                                18
                                            ]
                                        }
                                    ],
                                    "calorie": 1542.2
                                }
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

        mealService = MealService(client)
    }

    @Test
    fun testDeserializationOfMeal() = runTest(testDispatcher) {
        assertEquals(
            MealResponse(
                date = LocalDate(2024, 2, 1),
                exists = true,
                breakfast = MealDetailResponse(
                    listOf(
                        MenuResponse(
                            "쇠고기우엉볶음밥",
                            listOf(5, 6, 16),
                        ),
                        MenuResponse(
                            "불고기치즈파니니",
                            listOf(1, 2, 5, 6, 10, 13),
                        ),
                        MenuResponse(
                            "계란실파국",
                            listOf(1, 5, 6),
                        ),
                        MenuResponse(
                            "오이생채",
                            listOf(13),
                        ),
                        MenuResponse(
                            "배추김치",
                            listOf(9),
                        ),
                    ),
                    709.9f,
                ),
                lunch = MealDetailResponse(
                    listOf(
                        MenuResponse(
                            "발아현미밥",
                            listOf(),
                        ),
                        MenuResponse(
                            "맑은쇠고기무국1",
                            listOf(5, 6, 16),
                        ),
                        MenuResponse(
                            "매운갈비찜",
                            listOf(5, 6, 10, 13),
                        ),
                        MenuResponse(
                            "핫스모크연어스테이크/소스",
                            listOf(1, 2, 5, 6),
                        ),
                        MenuResponse(
                            "배추김치",
                            listOf(9),
                        ),
                        MenuResponse(
                            "눈사람도너츠",
                            listOf(1, 2, 5, 6),
                        ),
                    ),
                    1084.6f,
                ),
                dinner = MealDetailResponse(
                    listOf(
                        MenuResponse(
                            "추가밥",
                            listOf(),
                        ),
                        MenuResponse(
                            "돈코츠라멘",
                            listOf(1, 2, 5, 6, 10, 13, 15, 16),
                        ),
                        MenuResponse(
                            "오징어야채볶음",
                            listOf(5, 6, 13, 17),
                        ),
                        MenuResponse(
                            "배추김치",
                            listOf(9),
                        ),
                        MenuResponse(
                            "크럼블치킨/",
                            listOf(1, 2, 5, 6, 15, 18),
                        ),
                    ),
                    1542.2f,
                ),
            ),
            mealService.getMeal(2024, 2, 1),
        )
    }
}
