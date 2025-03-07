package com.b1nd.dodam.club.di

import com.b1nd.dodam.club.ClubViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val clubViewModelModule = module {
    viewModelOf(::ClubViewModel)
}