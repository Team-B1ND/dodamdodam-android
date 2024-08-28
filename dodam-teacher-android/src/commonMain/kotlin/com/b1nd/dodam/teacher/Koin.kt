package com.b1nd.dodam.teacher

import com.b1nd.dodam.common.network.di.dispatchersModule
import com.b1nd.dodam.data.login.di.loginRepositoryModule
import com.b1nd.dodam.datastore.di.dataStoreModule
import com.b1nd.dodam.login.di.loginViewModelModule
import com.b1nd.dodam.network.core.di.networkCoreModule
import com.b1nd.dodam.network.login.di.loginDataSourceModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(block: KoinApplication.() -> Unit = {}) {
    startKoin {
        modules(
            dataStoreModule,
            loginViewModelModule,
            loginRepositoryModule,
            loginDataSourceModule,
            networkCoreModule,
            dispatchersModule,
        )
        block()
    }
}
