package com.b1nd.dodam.parent.children_manage.di

import com.b1nd.dodam.parent.children_manage.ChildrenManageViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val childrenManageViewModelModule = module {
    viewModel { ChildrenManageViewModel() }
}
