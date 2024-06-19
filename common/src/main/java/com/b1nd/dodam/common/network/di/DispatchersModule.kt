package com.b1nd.dodam.common.network.di

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(DispatcherType.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(DispatcherType.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}

val dispatchersModule = module {
    factory<CoroutineDispatcher>(named(DispatcherType.IO)) { Dispatchers.IO }
    factory<CoroutineDispatcher>(named(DispatcherType.Default)) { Dispatchers.Default }
}
