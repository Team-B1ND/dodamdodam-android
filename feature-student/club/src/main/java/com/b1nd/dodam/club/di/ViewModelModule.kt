package com.b1nd.dodam.club.di

import com.b1nd.dodam.club.ClubViewModel
import com.b1nd.dodam.club.MyClubViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val myClubViewModelModule = module {
    viewModelOf(::MyClubViewModel)
}

val clubViewModelModule = module {
    viewModelOf(::ClubViewModel)
}
