package com.b1nd.dodam.network.meal.api

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.network.core.DodamUrl
import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.meal.datasource.MealDataSource
import com.b1nd.dodam.network.meal.model.CalorieResponse
import com.b1nd.dodam.network.meal.model.MealResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class MealService @Inject constructor(
    private val client: HttpClient,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : MealDataSource {
    override fun getMeal(year: Int, month: Int, day: Int): Flow<Response<MealResponse>> {
        return flow {
            emit(
                client.get(DodamUrl.MEAL) {
                    parameter("year", year)
                    parameter("month", month)
                    parameter("day", day)
                }.body<Response<MealResponse>>(),
            )
        }.flowOn(dispatcher)
    }

    override fun getCalorie(year: Int, month: Int, day: Int): Flow<Response<CalorieResponse>> {
        return flow {
            emit(
                client.get(DodamUrl.Meal.CALORIE) {
                    parameter("year", year)
                    parameter("month", month)
                    parameter("day", day)
                }.body<Response<CalorieResponse>>(),
            )
        }.flowOn(dispatcher)
    }

    override fun getMealOfMonth(year: Int, month: Int): Flow<Response<ImmutableList<CalorieResponse>>> {
        return flow {
            emit(
                client.get(DodamUrl.Meal.MONTH) {
                    parameter("year", year)
                    parameter("month", month)
                }.body<Response<ImmutableList<CalorieResponse>>>(),
            )
        }.flowOn(dispatcher)
    }

    override fun getCalorieOfMonth(year: Int, month: Int): Flow<Response<ImmutableList<CalorieResponse>>> {
        return flow {
            emit(
                client.get(DodamUrl.Meal.CALORIE_MONTH) {
                    parameter("year", year)
                    parameter("month", month)
                }.body<Response<ImmutableList<CalorieResponse>>>(),
            )
        }.flowOn(dispatcher)
    }
}
