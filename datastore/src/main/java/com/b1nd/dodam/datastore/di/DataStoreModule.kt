package com.b1nd.dodam.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.b1nd.dodam.common.Dispatcher
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.datastore.model.User
import com.b1nd.dodam.datastore.model.UserSerializer
import com.b1nd.dodam.datastore.repository.DatastoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataStoreModule = module {
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
