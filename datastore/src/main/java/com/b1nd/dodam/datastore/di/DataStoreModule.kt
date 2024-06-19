package com.b1nd.dodam.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.common.network.di.ApplicationScope
import com.b1nd.dodam.datastore.model.User
import com.b1nd.dodam.datastore.model.UserSerializer
import com.b1nd.dodam.datastore.repository.DatastoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.core.scope.get
import org.koin.dsl.module

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(DispatcherType.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        serializer: UserSerializer,
    ): DataStore<User> = DataStoreFactory.create(
        serializer = serializer,
        scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
    ) {
        context.dataStoreFile("preferences1.pb") // TODO("hilt 의존성 완전 제거시 삭제 필요")
    }
}

val DATA_STORE_MODULE = module {
    factory<UserSerializer> {
        UserSerializer()
    }

    single<DataStore<User>> {
        val context: Context = get()
        val ioDispatcher: CoroutineDispatcher = get(named(DispatcherType.IO))
        val scope: CoroutineScope = get()
        val serializer: UserSerializer = get()
        DataStoreFactory.create(
            serializer = serializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        ) {
            context.dataStoreFile("preferences.pb")
        }
    }

    single<DatastoreRepository> {
        DatastoreRepository(get(), get())
    }
}