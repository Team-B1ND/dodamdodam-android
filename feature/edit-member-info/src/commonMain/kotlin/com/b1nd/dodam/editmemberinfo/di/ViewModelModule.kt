package com.b1nd.dodam.editmemberinfo.di

import com.b1nd.dodam.editmemberinfo.viewmodel.EditmemberInfoViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editMemberInfoViewModelModule = module {
    viewModel { EditmemberInfoViewModel() }
}
