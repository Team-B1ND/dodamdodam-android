package com.b1nd.dodam.teacher

import com.b1nd.dodam.datastore.di.dataStoreModule
import com.b1nd.dodam.keystore.keystoreManagerModule
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication

import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(
    block: KoinApplication.() -> Unit = {}
) {
    startKoin {
        modules(
            dataStoreModule,
            testViewModelModule,
            keystoreManagerModule
        )
        block()
    }
}

val testViewModelModule = module {
    viewModel {
        TestViewModel()
    }
}