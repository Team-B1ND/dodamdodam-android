package com.b1nd.dodam.club.di


import com.b1nd.dodam.club.MyClubViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.b1nd.dodam.club.ClubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myClubViewModelModule = module {
    viewModelOf(::MyClubViewModel)
}


val clubViewModelModule = module {
    viewModel { ClubViewModel() }
}

