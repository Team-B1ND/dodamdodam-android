package com.b1nd.dodam.meal.di

import com.b1nd.dodam.meal.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mealViewModelModule = module {
    viewModel { MealViewModel() }
}
