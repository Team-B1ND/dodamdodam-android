package com.b1nd.dodam.teacher.di


import com.b1nd.dodam.teacher.DodamTeacherAppViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DodamTeacherAppViewModelModule = module {
    viewModel {
        DodamTeacherAppViewModel()
    }
}