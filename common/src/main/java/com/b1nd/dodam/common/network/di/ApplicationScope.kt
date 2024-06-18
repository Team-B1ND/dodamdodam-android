package com.b1nd.dodam.common.network.di

import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopesModule {
    @Provides
    @Singleton
    @ApplicationScope
    fun providesCoroutineScope(@Dispatcher(DispatcherType.Default) dispatcher: CoroutineDispatcher): CoroutineScope =
        CoroutineScope(SupervisorJob() + dispatcher)
}


val COROUTINE_SCOPE_MODULE = module {
    single<CoroutineScope> {
        val dispatcher: CoroutineDispatcher = get(named(DispatcherType.Default))
        CoroutineScope(SupervisorJob() + dispatcher)
    }
}