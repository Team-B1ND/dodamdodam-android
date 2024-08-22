package com.b1nd.dodam.teacher

import com.b1nd.dodam.datastore.di.dataStoreModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(block: KoinApplication.() -> Unit = {}) {
    startKoin {
        modules(
            dataStoreModule,
        )
        block()
    }
}
