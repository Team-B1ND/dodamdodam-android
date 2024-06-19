package com.b1nd.dodam.network.meal.api

import android.util.Log
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.core.util.safeRequest
import com.b1nd.dodam.network.meal.datasource.MealDataSource
import com.b1nd.dodam.network.meal.model.MealResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal class MealService @Inject constructor(
    private val client: HttpClient,
) : MealDataSource {
    override suspend fun getMeal(year: Int, month: Int, day: Int): MealResponse {
        return safeRequest {
            Log.d("TAG", "getMeal: called service")
            client.get(DodamUrl.MEAL) {
                parameter("year", year)
                parameter("month", month)
                parameter("day", day)
            }.body<Response<MealResponse>>()
        }
    }

    override suspend fun getMealOfMonth(year: Int, month: Int): ImmutableList<MealResponse> {
        return safeRequest {
            client.get(DodamUrl.Meal.MONTH) {
                parameter("year", year)
                parameter("month", month)
            }.body<Response<List<MealResponse>>>()
        }.toImmutableList()
    }
}
