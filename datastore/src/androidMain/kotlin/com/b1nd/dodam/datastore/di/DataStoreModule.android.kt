package com.b1nd.dodam.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.datastore.createDataStore
import com.b1nd.dodam.datastore.model.User
import com.b1nd.dodam.datastore.repository.DatastoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val dataStoreModule: Module = module {
    single<DataStore<Preferences>> {
        val context: Context = get()
        createDataStore(context)
    }

    single<DatastoreRepository> {
        DatastoreRepository(get(), get())
    }
}
