package com.b1nd.dodam.student.main.di

import com.b1nd.dodam.meal.di.mealViewModelModule
import com.b1nd.dodam.member.di.memberViewModelModule
import com.b1nd.dodam.nightstudy.di.nightStudyViewModelModule
import com.b1nd.dodam.outing.di.outingViewModelModule
import com.b1nd.dodam.student.home.di.homeViewModelModule

val mainViewModelModules = listOf(
    memberViewModelModule,
    homeViewModelModule,
    nightStudyViewModelModule,
    outingViewModelModule,
    mealViewModelModule,
)
