package com.b1nd.dodam.data.meal.respository

import android.util.Log
import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.common.result.asResult
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.meal.model.Meal
import com.b1nd.dodam.data.meal.model.toModel
import com.b1nd.dodam.network.meal.datasource.MealDataSource
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class MealRepositoryImpl @Inject constructor(
    private val network: MealDataSource,
    @Dispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : MealRepository {
    override fun getMeal(year: Int, month: Int, day: Int): Flow<Result<Meal>> {
        return flow {
            Log.d("TAG", "getMeal: repo called")
            emit(network.getMeal(year, month, day).toModel())
        }
            .asResult()
            .flowOn(dispatcher)
    }

    override fun getMealOfMonth(year: Int, month: Int): Flow<Result<ImmutableList<Meal>>> {
        return flow {
            emit(network.getMealOfMonth(year, month).map { it.toModel() }.toImmutableList())
        }
            .asResult()
            .flowOn(dispatcher)
    }
}
