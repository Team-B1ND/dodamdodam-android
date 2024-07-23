package com.b1nd.dodam.common.network.di

import com.b1nd.dodam.common.DispatcherType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {
    factory<CoroutineDispatcher>(named(DispatcherType.IO)) { Dispatchers.IO }
    factory<CoroutineDispatcher>(named(DispatcherType.Default)) { Dispatchers.Default }
}
