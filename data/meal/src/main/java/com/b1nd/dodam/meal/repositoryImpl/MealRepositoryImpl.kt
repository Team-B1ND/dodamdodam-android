package com.b1nd.dodam.meal.repositoryImpl

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.meal.repository.MealRepository
import com.b1nd.dodam.model.Meal
import com.b1nd.dodam.network.meal.datasource.MealDataSource
import com.b1nd.dodam.network.meal.model.CalorieResponse
import com.b1nd.dodam.network.meal.model.MealResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

internal class MealRepositoryImpl @Inject constructor(
    private val mealDataSource: MealDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : MealRepository {
    override fun getMeal(year: Int, month: Int, day: Int): Flow<Result<Meal>> {
        return flow {
            val mealResponse = mealDataSource.getMeal(year, month, day)
            val calorieResponse = mealDataSource.getCalorie(year, month, day)
            emit(
                Meal(
                    mealResponse.date,
                    mealResponse.exists,
                    mealResponse.breakfast,
                    mealResponse.lunch,
                    mealResponse.dinner,
                    calorieResponse.exists,
                    calorieResponse.breakfast,
                    calorieResponse.lunch,
                    calorieResponse.dinner
                )
            )
        }.asResult().flowOn(dispatcher)
    }

    override fun getMealOfMonth(
        year: Int,
        month: Int
    ): Flow<Result<ImmutableList<Meal>>> {
        return flow<ImmutableList<MealResponse>> { mealDataSource.getMealOfMonth(year, month) }.zip(
            flow<ImmutableList<CalorieResponse>> { mealDataSource.getCalorieOfMonth(year, month) }
        ) { mealResponse, calorieResponse ->
            mealResponse.zip(calorieResponse) { meal, calorie ->
                Meal(
                    meal.date,
                    meal.exists,
                    meal.breakfast,
                    meal.lunch,
                    meal.dinner,
                    calorie.exists,
                    calorie.breakfast,
                    calorie.lunch,
                    calorie.dinner
                )
            }.toImmutableList()
        }.asResult().flowOn(dispatcher)
    }
}
