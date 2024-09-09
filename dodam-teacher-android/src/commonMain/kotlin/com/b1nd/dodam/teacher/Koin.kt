package com.b1nd.dodam.teacher

import com.b1nd.dodam.common.network.di.coroutineScopeModule
import com.b1nd.dodam.common.network.di.dispatchersModule
import com.b1nd.dodam.data.login.di.loginRepositoryModule
import com.b1nd.dodam.datastore.di.dataStoreModule
import com.b1nd.dodam.login.di.loginViewModelModule
import com.b1nd.dodam.network.core.di.networkCoreModule
import com.b1nd.dodam.network.login.di.loginDataSourceModule
import com.b1nd.dodam.nightstudy.di.nightStudyViewModelModule
import com.b1nd.dodam.register.di.registerDataSourceModule
import com.b1nd.dodam.register.di.registerRepositoryModule
import com.b1nd.dodam.register.di.registerViewModelModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(block: KoinApplication.() -> Unit = {}) {
    startKoin {
        modules(
            dataStoreModule,
            coroutineScopeModule,
            dispatchersModule,
            networkCoreModule,
            registerViewModelModule,
            registerRepositoryModule,
            registerDataSourceModule,
            loginViewModelModule,
            loginRepositoryModule,
            loginDataSourceModule,
            nightStudyViewModelModule,
        )
        block()
    }
}
