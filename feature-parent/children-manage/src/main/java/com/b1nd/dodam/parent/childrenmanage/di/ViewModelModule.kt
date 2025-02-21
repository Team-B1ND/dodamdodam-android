package com.b1nd.dodam.parent.childrenmanage.di

import com.b1nd.dodam.parent.childrenmanage.ChildrenManageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val childrenManageViewModelModule = module {
    viewModel { ChildrenManageViewModel() }
}
