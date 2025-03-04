package com.b1nd.dodam.join_club.di

import com.b1nd.dodam.join_club.JoinClubViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val joinClubViewModelModule = module {
    viewModel { JoinClubViewModel() }
}
